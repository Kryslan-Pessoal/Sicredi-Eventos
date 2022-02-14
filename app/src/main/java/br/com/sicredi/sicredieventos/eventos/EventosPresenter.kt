package br.com.sicredi.sicredieventos.eventos

import br.com.sicredi.sicredieventos.entidades.Evento
import br.com.sicredi.sicredieventos.eventos.view.EventosView
import org.json.JSONArray

class EventosPresenter(eventosView: EventosView) {


    val model = EventosModel(this)
    val view = eventosView

    fun processaBuscaEventos(retornoString: String) {

        var retornoJson = JSONArray(retornoString)



        val eventos: ArrayList<Evento> = ArrayList()
        val evento = Evento(
            1,
            "Feira de adoção de animais na Redenção",
            "O Patas Dadas estará na Redenção, nesse domingo, com cães \n" +
                    "para adoção e produtos à venda...",
            null,
            123,
            "",
            123,
            123,
            29.99
        )
        eventos.add(evento)

        view.recebeEventos(eventos)
    }

}