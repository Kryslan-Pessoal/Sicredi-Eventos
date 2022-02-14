package br.com.sicredi.sicredieventos.utilitarios.LoadedV2;

import android.os.AsyncTask;
import android.os.Build;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;


/** Classe usada para fazer solicitações WebServices com o url e os parâmetros.
 * Utilize o método {@link #send} para passar os parâmetros e mandar a solicitação web.
 * Utilize a interface {@link LoadedV2} para receber a resposta.
 *  Ex: SolicitacaoGet solicitacaoGet = new SolicitacaoGet();
    solicitacaoGet.send("seuUrl", new Loaded(){
        //Implemente os métodos obrigatórios!
    })
 */
public class SolicitacaoPost extends AsyncTask<Void, String, String> {

    //region Globais
    /** Listener que recebe o resultado do servidor */
    private LoadedV2 loadedV2;
    /** URL da solicitação */
    private String url;
    /** Corpo da solicitação POST */
    private String jsonBody;
    /** boolean para o código saber no final do processo se a operação foi bem sucedida ou não */
    private boolean seSucesso = false;
    /** Caso seja gerado uma exceção */
    private Exception exception = null;
    //endregion Globais

    //region Solicitação
    public void send(String url, String jsonBody, final LoadedV2 loadedV2) {
        this.loadedV2 = loadedV2;
        this.url = url;
        this.jsonBody = jsonBody;

        //Executa em segundo plano o doInBackground(); abaixo
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    //endregion Solicitação

    //region Processamento
    @Override
    protected String doInBackground(Void... voids) {

        String resultado;  //Resultado final com a resposta do servidor ou erro (caso ocorra)

        String urlLink = url;
        try{
            /* Configuração Inicial */
            URL url = new URL(urlLink);
            //Objeto que gerencia a conexão
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            //Método de Postagem
            conexao.setRequestMethod("POST");

            /* HEADERS */
            //Define que o retorno será em JSON (Pode ser alterado caso o servidor retorne de outra forma)
            conexao.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            OutputStream os = conexao.getOutputStream();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            }else{
                os.write(jsonBody.getBytes("UTF-8"));
            }

            os.close();

            int codigoDaRespostaDoServidor = conexao.getResponseCode();
            if(codigoDaRespostaDoServidor == HttpsURLConnection.HTTP_OK || codigoDaRespostaDoServidor == 201){
                //Sucesso na Conexão
                resultado = pegaRetornoDoServidor(conexao);
                seSucesso = true;
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
        while((pedacosDoRetorno = buff.readLine()) != null){
            dadosRetornoStringBuilder.append(pedacosDoRetorno);
        }

        return dadosRetornoStringBuilder.toString();
    }
    //endregion Processamento
}
