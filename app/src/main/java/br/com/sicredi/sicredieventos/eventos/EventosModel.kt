package br.com.sicredi.sicrediEventos.eventos

import br.com.sicredi.sicrediEventos.utilitarios.Erros
import br.com.sicredi.sicrediEventos.utilitarios.LoadedV2.LoadedV2
import br.com.sicredi.sicrediEventos.utilitarios.LoadedV2.SolicitacaoGet
import br.com.sicredi.sicrediEventos.utilitarios.Constantes
import java.lang.Exception

class EventosModel (eventosPresenter: EventosPresenter) {

    private val presenter = eventosPresenter

    fun buscaEventos() {

        val url = Constantes.BASE_API + "events"

        val solicitacaoGet = SolicitacaoGet()

        val impl = object : LoadedV2 {
            /** Quando a resposta do Servidor é bem sucedida
             * @param retorno: Retorno do servidor (convertido para String)
             */
            override fun onResponse(retorno: String) {

                presenter.processaBuscaEventos(retorno)

            }

            /** Quando ocorre algum erro na comunicação
             * @param retorno: Erro retornado
             * @param throwable: Exception lançada (caso tenha) (pode ser null se não tiver)
             */
            override fun onFailure(retorno: String, throwable: Exception?) {

                presenter.view.erro(
                    Erros.geraMensagemDeErro(
                    "Erro ao buscar eventos no Servidor!",
                    Erros.ERRO1,
                    throwable
                ))

            }
        }

        solicitacaoGet.send(url, impl)

    }

}