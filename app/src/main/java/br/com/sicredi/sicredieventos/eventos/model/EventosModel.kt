package br.com.sicredi.sicredieventos.eventos.model

import br.com.sicredi.sicredieventos.eventos.presenter.EventosPresenter

class EventosModel (eventosPresenter: EventosPresenter) {

    private val presenter = eventosPresenter

    fun buscaEventos() {
        presenter.processaBuscaEventos()
    }

}