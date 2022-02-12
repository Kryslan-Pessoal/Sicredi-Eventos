package br.com.sicredi.sicredieventos.eventos

class EventosPresenter(eventosView: EventosView) {


    val model = EventosModel(this)
    val view = eventosView

    fun processaBuscaEventos() {
        view.recebeEventos(22)
    }

}