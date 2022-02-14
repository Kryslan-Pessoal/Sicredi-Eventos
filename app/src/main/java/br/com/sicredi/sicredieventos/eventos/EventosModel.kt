package br.com.sicredi.sicredieventos.eventos

import br.com.sicredi.sicredieventos.utilitarios.Constantes
import br.com.sicredi.sicredieventos.utilitarios.LoadedV2.LoadedV2
import br.com.sicredi.sicredieventos.utilitarios.LoadedV2.SolicitacaoGet
import java.lang.Exception

class EventosModel (eventosPresenter: EventosPresenter) {

    private val presenter = eventosPresenter

    fun buscaEventos() {

        val url = Constantes.API_GET_EVENTOS + "events"

        val solicitacaoGet = SolicitacaoGet()

        val impl = object : LoadedV2 {
            /** Quando a resposta do Servidor é bem sucedida
             * @param retorno: Retorno do servidor (convertido para String)
             */
            override fun onResponse(retorno: String?) {
                var eu = 22;
            }

            /** Quando ocorre algum erro na comunicação
             * @param retorno: Erro retornado
             * @param throwable: Exception lançada (caso tenha) (pode ser null se não tiver)
             */
            override fun onFailure(retorno: String?, throwable: Exception?) {
                var eu = 22;
            }
        }

        solicitacaoGet.send(url, impl)

        presenter.processaBuscaEventos()
    }

}