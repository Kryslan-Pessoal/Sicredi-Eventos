package br.com.sicredi.sicrediEventos.dadosDosEventos

import br.com.sicredi.sicrediEventos.utilitarios.Erros
import org.json.JSONObject

class DadosDoEventoPresenter(dadosDoEventoView: DadosDoEventoView) {

    val model = DadosDoEventoModel(this)
    val view = dadosDoEventoView

    fun processaRetornoCheckIn(retornoString: String) {

        try {

            val jsonObject = JSONObject(retornoString)

            val codigoDeRetorno = jsonObject.getString("code")

            if(codigoDeRetorno.equals("200"))
                view.sucessoCheckIn()
            else
                view.erro("Check-in Não foi Feito, código de erro: $codigoDeRetorno.")

        }catch (e: Exception){
            view.erro(
                Erros.geraMensagemDeErro(
                "Erro ao processar os eventos retornados do Servidor!",
                Erros.ERRO3,
                e
            ))
        }

    }
}