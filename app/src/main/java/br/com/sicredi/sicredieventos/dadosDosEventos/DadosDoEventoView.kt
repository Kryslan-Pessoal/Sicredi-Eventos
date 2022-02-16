package br.com.sicredi.sicrediEventos.dadosDosEventos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import br.com.sicredi.sicrediEventos.entidades.Evento
import br.com.sicredi.sicrediEventos.utilitarios.Util.Companion.converteDoubleEmMoeda
import br.com.sicredi.sicredieventos.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DadosDoEventoView : AppCompatActivity() {

    //region Globais
    private lateinit var evento: Evento
    //endregion Globais

    //region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_do_evento)

        carregaToolbar()

        getDadosDoIntent()

        setDadosNosViews()

        listeners()

    }
    private fun listeners() {
        listenerBotaoMapa()
        listenerBotaoCompartilhar()
        listenerBotaoCheckIn()
    }
    private fun getDadosDoIntent() {
        //evento = Evento()

        evento.id = intent.getIntExtra("id", 0)
        evento.title = intent.getStringExtra("title")!!
        evento.description = intent.getStringExtra("description")!!
        evento.date = intent.getLongExtra("date", 0)
        evento.image = intent.getStringExtra("image")!!
        evento.latitude = intent.getDoubleExtra("latitude", 0.0)
        evento.longitude = intent.getDoubleExtra("longitude", 0.0)
        evento.price = intent.getDoubleExtra("price", 0.0)
    }
    //endregion onCreate

    //region Views
    private fun setDadosNosViews() {

        //Imagem
        val imagemEvento_imageView: ImageView = findViewById(R.id.imagemEvento_imageView)
        Picasso.get().load(evento.image).into(imagemEvento_imageView, object: Callback {
            override fun onSuccess() {
                Picasso.get().load(evento.image).into(imagemEvento_imageView)
            }
            override fun onError(e: Exception?) {
                Picasso.get().load(R.drawable.sicredi_eventos_logo).into(imagemEvento_imageView)
            }
        })

        //Textos
        findViewById<TextView>(R.id.titulo_textView).text = evento.title
        //TODO: data não foi especificada como será feita a conversão
        //findViewById<TextView>(R.id.data_textView).text = evento.date + ""
        findViewById<TextView>(R.id.preco_textView).text = converteDoubleEmMoeda(evento.price)
        findViewById<TextView>(R.id.descricao_textView).text = evento.description

        //Localização
        abreMapa()
    }
    //region Views

    //region Buttons
    private fun listenerBotaoMapa() {

    }
    private fun listenerBotaoCompartilhar() {

    }
    private fun listenerBotaoCheckIn() {

    }
    //endregion Buttons

    //region Utilitarios
    private fun carregaToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    private fun abreMapa() {

    }
    //endregion Utilitarios
}