package br.com.sicredi.sicredieventos.eventos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.sicredi.sicredieventos.R
import br.com.sicredi.sicredieventos.eventos.presenter.EventosPresenter

class EventosView : AppCompatActivity() {

    var presenter = EventosPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        buscaEventos()

    }

    private fun buscaEventos(){

        presenter.model.buscaEventos()
    }

    fun recebeEventos(valor: Int) {
        Toast.makeText(this, "EVENTOS RECEBIDOS" + valor, Toast.LENGTH_SHORT).show()
    }
}