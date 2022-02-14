package br.com.sicredi.sicredieventos.eventos

import br.com.sicredi.sicredieventos.entidades.Evento
import br.com.sicredi.sicredieventos.eventos.view.EventosView
import br.com.sicredi.sicredieventos.utilitarios.Erros
import com.google.gson.Gson
import org.json.JSONArray

class EventosPresenter(eventosView: EventosView) {

    val model = EventosModel(this)
    val view = eventosView

    fun processaBuscaEventos(retornoString: String) {

        try {

            val eventos: ArrayList<Evento> = ArrayList()
            val gson = Gson()
            val jsonArray = JSONArray(retornoString)

            for (i in 0 until jsonArray.length()) {
                val evento = gson.fromJson(jsonArray.get(i).toString(), Evento::class.java)
                eventos.add(evento)
            }

            view.recebeEventos(eventos)

        }catch (e: Exception){

            view.erro(Erros.geraMensagemDeErro(
                "Erro ao processar os eventos retornados do Servidor!",
                Erros.ERRO0,
                e
            ))

        }

    }
}