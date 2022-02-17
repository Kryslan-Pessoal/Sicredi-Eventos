package br.com.sicredi.sicrediEventos.utilitarios.LoadedV2;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;


/** Classe usada para fazer solicitações WebServices apenas com o url.
 * Utilize o método {@link #send} para passar os parâmetros e mandar a solicitação web.
 * Utilize a interface {@link LoadedV2} para receber a resposta.
 * Ex: SolicitacaoGet solicitacaoGet = new SolicitacaoGet();
   solicitacaoGet.send("seuUrl", new Loaded(){
       //Implemente os métodos obrigatórios!
   })
 */
public class SolicitacaoGet extends AsyncTask<Void, String, String> {

    //region Globais
    /** Listener que recebe o resultado do servidor */
    private LoadedV2 loadedV2;
    /** URL com os parâmetros GET já inseridos */
    private String url;
    /** boolean para o código saber no final do processo se a operação foi bem sucedida ou não */
    private boolean seSucesso = false;
    /** Caso seja gerado uma exceção */
    private Exception exception = null;
    //endregion Globais

    //region Solicitação
    public void send(String url, final LoadedV2 loadedV2) {
        this.loadedV2 = loadedV2;
        this.url = url;

        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);  //Executa em segundo plano o doInBackground(); abaixo
    }
    //endregion Solicitação

    //region Processamento
    @Override
    protected String doInBackground(Void... voids){

        String resultado = "";  //Resultado final com a resposta do servidor ou erro (caso ocorra)

        String urlLink = url;
        try{
            /* Configuração Inicial */
            URL url = new URL(urlLink);
            //Objeto que gerencia a conexão
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            //Método de Postagem
            conexao.setRequestMethod("GET");

            /* HEADERS */
            //Define que o retorno será em JSON (Pode ser alterado caso o servidor retorne de outra forma)
            conexao.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            int codigoDaRespostaDoServidor = conexao.getResponseCode();
            if(
                codigoDaRespostaDoServidor == HttpsURLConnection.HTTP_OK ||  //200: Sucesso
                codigoDaRespostaDoServidor == HttpURLConnection.HTTP_CREATED  //201: Sucesso na criação de algo
            ){
                //Sucesso na Conexão
                resultado = pegaRetornoDoServidor(conexao);
                seSucesso = true;
            }else if(codigoDaRespostaDoServidor == HttpURLConnection.HTTP_BAD_REQUEST){  //400 erro interno do servidor, porém o servidor retornou a mensagem do erro
                //Sucesso na Conexão, mas falha interna no servidor
                resultado = ERRO_400;
                seSucesso = false;
            }else{
                //Falha na Conexão
                resultado = "Erro de Conexão " + codigoDaRespostaDoServidor + " com o servidor!";
                seSucesso = false;
            }
        }catch (UnknownHostException hostDesconhecido){
            //Quando o usuário não está conectado a VPN
            resultado = "Host desconhecido";
            seSucesso = false;
            exception = hostDesconhecido;
        }catch (Exception e){
            //Qualquer outra exceção
            resultado = "Erro de Conexão, Detalhes do erro: " + e.getMessage();
            seSucesso = false;
            exception = e;
        }

        return resultado;
    }
    /** Resultado do {@link #doInBackground(Void...)} */
    @Override
    protected void onPostExecute(String resultado) {
        super.onPostExecute(resultado);

        //Retorna o resultado na Interface
        if(seSucesso)
            loadedV2.onResponse(resultado);
        else
            loadedV2.onFailure(resultado, exception);
    }

    /** Pega o retorno do Servidor e o converte em String */
    private String pegaRetornoDoServidor(HttpURLConnection conexao) throws IOException {
        InputStream inputStream = new BufferedInputStream(conexao.getInputStream());
        InputStreamReader leitorInputStream = new InputStreamReader(inputStream);
        BufferedReader buff = new BufferedReader(leitorInputStream);
        StringBuilder dadosRetornoStringBuilder = new StringBuilder();
        String pedacosDoRetorno ;
        while((pedacosDoRetorno = buff.readLine()) != null) {
            dadosRetornoStringBuilder.append(pedacosDoRetorno);
        }

        return dadosRetornoStringBuilder.toString();
    }
    //endregion Processamento

    //region Enum
    public static final String ERRO_400 = "Erro interno no Servidor! (Erro 400)";
    //endregion Enum
}
