package br.com.sicredi.sicredieventos.utilitarios.LoadedV2;

import androidx.annotation.Nullable;

/** Interface criada por Kryslan Gomes (kryslan2680@gmail.com)
 * Interface que recebe a resposta do servidor de uma solicitação WebService.
 * Ao receber a resposta do servidor, esta interface é ativa, e o retorno é feito nos parâmetros abaixo
 * */
public interface LoadedV2 {

    /** Quando a resposta do Servidor é bem sucedida
     * @param retorno: Retorno do servidor (convertido para String) **/
    void onResponse(String retorno);

    /** Quando ocorre algum erro na comunicação
     * @param retorno: Erro retornado
     * @param throwable: Exception lançada (caso tenha) (pode ser null se não tiver) ***/
    void onFailure(String retorno, @Nullable Exception throwable);

}
