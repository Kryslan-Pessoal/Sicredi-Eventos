package br.com.sicredi.sicredieventos.eventos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.sicredi.sicredieventos.R
import br.com.sicredi.sicredieventos.databinding.ActivityEventosBinding
import br.com.sicredi.sicredieventos.entidades.Evento
import br.com.sicredi.sicredieventos.eventos.EventosPresenter

class EventosView : AppCompatActivity() {

    //region Globais
    var presenter = EventosPresenter(this)
    private lateinit var binding: ActivityEventosBinding

    private lateinit var eventos: ArrayList<Evento>
    //endregion Globais

    //region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configuraToolbar()

        buscaEventos()

    }
    //endregion onCreate

    //region Busca Eventos
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
    fun recebeEventos(eventos: ArrayList<Evento>) {

        this.eventos = eventos

        preencheListView()

    }


    //endregion Busca Eventos

    //region Preenche ListView com Eventos
    private fun preencheListView() {

        binding.eventosListView.isClickable = true
        binding.eventosListView.adapter = EventosAdapter(this, eventos)
        binding.eventosListView.setOnItemClickListener {parent, view, position, id ->
            Toast.makeText(this, "Clicado: " + eventos[position].title, Toast.LENGTH_SHORT).show()
        }

    }
    //region Preenche ListView com Eventos

    //region Utilitarios
    private fun configuraToolbar() {
        supportActionBar?.title = "Eventos"
        setSupportActionBar(findViewById(R.id.toolbar))
    }
    //endregion Utilitarios
}