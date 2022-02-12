package br.com.sicredi.sicredieventos.eventos

class EventosModel (eventosPresenter: EventosPresenter) {

    private val presenter = eventosPresenter

    fun buscaEventos() {
        presenter.processaBuscaEventos()
    }

}