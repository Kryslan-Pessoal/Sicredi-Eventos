package br.com.sicredi.sicrediEventos.eventos.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import br.com.sicredi.sicrediEventos.dadosDosEventos.DadosDoEventoView
import br.com.sicredi.sicredieventos.R
import br.com.sicredi.sicredieventos.databinding.ActivityEventosBinding
import br.com.sicredi.sicrediEventos.entidades.Evento
import br.com.sicredi.sicrediEventos.eventos.EventosPresenter
import java.lang.Exception

class EventosView : AppCompatActivity() {

    //region Globais
    var presenter = EventosPresenter(this)
    private lateinit var binding: ActivityEventosBinding

    private lateinit var eventos: ArrayList<Evento>

    private lateinit var progressDialog: ProgressDialog
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

        showLoading()

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

        hideLoading()

        binding.eventosListView.isClickable = true
        binding.eventosListView.adapter = EventosAdapter(this, eventos)
        binding.eventosListView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DadosDoEventoView::class.java)

            val evento = eventos[position]

            intent.putExtra("id", evento.id)
            intent.putExtra("title", evento.title)
            intent.putExtra("description", evento.description)
            intent.putExtra("date", evento.date)
            intent.putExtra("image", evento.image)
            intent.putExtra("latitude", evento.latitude)
            intent.putExtra("longitude", evento.longitude)
            intent.putExtra("price", evento.price)

            startActivity(intent)
        }

    }
    //region Preenche ListView com Eventos

    //region Utilitarios
    private fun configuraToolbar() {
        supportActionBar?.title = "Eventos"
        setSupportActionBar(findViewById(R.id.toolbar))
    }
    fun erro(mensagemDeErro: String){

        hideLoading()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erro!")
        builder.setMessage(mensagemDeErro)
        builder.setPositiveButton("Ok"){ _, _ -> }
        builder.setIcon(R.drawable.ic_erro)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showLoading(){
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Buscando Eventos...")
        progressDialog.show()
    }
    private fun hideLoading(){
        try {
            progressDialog.hide()
        }catch (ignored: Exception){}
    }
    //endregion Utilitarios
}