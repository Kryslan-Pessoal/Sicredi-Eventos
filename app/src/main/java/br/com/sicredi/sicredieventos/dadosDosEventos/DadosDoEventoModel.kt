package br.com.sicredi.sicrediEventos.dadosDosEventos

import br.com.sicredi.sicrediEventos.utilitarios.Constantes
import br.com.sicredi.sicrediEventos.utilitarios.Erros
import br.com.sicredi.sicrediEventos.utilitarios.LoadedV2.LoadedV2
import br.com.sicredi.sicrediEventos.utilitarios.LoadedV2.SolicitacaoPost
import org.json.JSONStringer

class DadosDoEventoModel (dadosDoEventoPresenter: DadosDoEventoPresenter) {

    private val presenter = dadosDoEventoPresenter

    fun fazCheckIn(eventId: Int, name: String, email: String) {

        val url = Constantes.BASE_API + "checkin"

        val solicitacaoPost = SolicitacaoPost()

        val bodyJsonString: String = montaJsonStringCheckIn(eventId, name, email)

        val impl = object : LoadedV2 {
            /** Quando a resposta do Servidor é bem sucedida
             * @param retorno: Retorno do servidor (convertido para String)
             */
            override fun onResponse(retorno: String) {

                presenter.processaRetornoCheckIn(retorno)

            }

            /** Quando ocorre algum erro na comunicação
             * @param retorno: Erro retornado
             * @param throwable: Exception lançada (caso tenha) (pode ser null se não tiver)
             */
            override fun onFailure(retorno: String, throwable: Exception?) {

                presenter.view.erro(
                    Erros.geraMensagemDeErro(
                    "Erro ao fazer check-in no Evento!",
                    Erros.ERRO2,
                    throwable
                ))

            }
        }

        solicitacaoPost.send(url, bodyJsonString, impl)

    }

    //endregion Confirmar Carregamento/Descarregamento
    private fun montaJsonStringCheckIn( eventId: Int, name: String, email: String): String {
        val body = JSONStringer()
        try {
            body
                .`object`()
                    .key("eventId").value(eventId)
                    .key("name").value(name)
                    .key("email").value(email)
                .endObject()
        } catch (ignored: Exception) {}
        return body.toString()
    }
}