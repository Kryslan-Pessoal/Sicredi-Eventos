package br.com.sicredi.sicredieventos.eventos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.sicredi.sicredieventos.R

class EventosView : AppCompatActivity() {

    //region Globais
    var presenter = EventosPresenter(this)
    //endregion Globais

    //region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        configuraToolbar()

        buscaEventos()

    }
    //endregion onCreate

    //region Eventos
    /**
     * Busca eventos no servidor.
     * Retorno em {@link #recebeEventos}
     */
    private fun buscaEventos(){

        presenter.model.buscaEventos()

    }
    /**
     * Exibe os eventos.
     * Solicitado em {@link #buscaEventos}
     */
    fun recebeEventos(valor: Int) {
        Toast.makeText(this, "EVENTOS RECEBIDOS" + valor, Toast.LENGTH_SHORT).show()
    }
    //endregion Eventos

    //region Utilitarios
    private fun configuraToolbar() {
        supportActionBar?.title = "Eventos"
        setSupportActionBar(findViewById(R.id.toolbar))
    }
    //endregion Utilitarios
}