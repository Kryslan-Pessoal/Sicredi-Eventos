package br.com.sicredi.sicredieventos.eventos

import br.com.sicredi.sicredieventos.eventos.view.EventosView

class EventosPresenter(eventosView: EventosView) {


    val model = EventosModel(this)
    val view = eventosView

    fun processaBuscaEventos() {
        view.recebeEventos(22)
    }

}