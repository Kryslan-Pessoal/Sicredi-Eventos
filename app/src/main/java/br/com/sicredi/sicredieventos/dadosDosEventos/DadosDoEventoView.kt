package br.com.sicredi.sicrediEventos.dadosDosEventos

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.sicredi.sicrediEventos.entidades.Evento
import br.com.sicredi.sicrediEventos.utilitarios.Util.Companion.converteDoubleEmMoeda
import br.com.sicredi.sicrediEventos.utilitarios.Util.Companion.converteMilisegundosEmData
import br.com.sicredi.sicredieventos.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class DadosDoEventoView : AppCompatActivity() {

    //region Globais
    private lateinit var evento: Evento

    private lateinit var progressDialog: ProgressDialog
    var presenter = DadosDoEventoPresenter(this)
    private var nome = ""

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
        evento = Evento()

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
    @SuppressLint("SetTextI18n")
    private fun setDadosNosViews() {

        //Imagem
        val imagemEvento_imageView: ImageView = findViewById(R.id.imagemEvento_imageView)
        Picasso.get().load(evento.image).into(imagemEvento_imageView, object: Callback {
            override fun onSuccess() {
                Picasso.get().load(evento.image).into(imagemEvento_imageView)
            }
            override fun onError(e: Exception?) {
                Picasso.get().load(R.drawable.evento_comum).into(imagemEvento_imageView)
            }
        })

        //Textos
        findViewById<TextView>(R.id.titulo_textView).text = evento.title
        findViewById<TextView>(R.id.data_textView).text = converteMilisegundosEmData(evento.date)
        findViewById<TextView>(R.id.preco_textView).text = converteDoubleEmMoeda(evento.price)
        findViewById<TextView>(R.id.descricao_textView).text = evento.description
    }
    //endregion Views

    //region Buttons
    private fun listenerBotaoMapa() {
        findViewById<FloatingActionButton>(R.id.abrirNoMapa_fab).setOnClickListener{
            abreMapa()
        }
    }
    private fun listenerBotaoCompartilhar() {
        findViewById<FloatingActionButton>(R.id.compartilhar_fab).setOnClickListener{
            compartilhar()
        }
    }
    private fun listenerBotaoCheckIn() {
        findViewById<Button>(R.id.checkIn_button).setOnClickListener{
            pegaNomeDoUsuarioParaCheckIn()
        }
    }
    //endregion Buttons

    //region Mapa
    private fun abreMapa() {
        val uri =
            "https://www.google.com/maps/dir/?api=1&destination=" +
                    evento.latitude +
                    "," +
                    evento.longitude

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }
    //endregion Mapa

    //region Compartilhar
    private fun compartilhar(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND

            val dataDoEvento = converteMilisegundosEmData(evento.date)

            val texto = "Hey, ficou sabendo do evento que vai ter dia $dataDoEvento?\n" +
                    "O Evento vai ser: ${evento.title}! Não podemos perder!"

            putExtra(Intent.EXTRA_TEXT, texto)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
    //endregion Compartilhar

    //region Check-in
    private fun pegaNomeDoUsuarioParaCheckIn() {

        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Dados Para Check-In")

        val nome_editText = EditText(this)
        nome_editText.hint = "Nome"
        nome_editText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(nome_editText)

        builder.setPositiveButton("OK") { dialog, _ ->
            nome = nome_editText.text.toString()

            if(nome.isNotEmpty()){
                pegaEmailUsuarioParaCheckIn()
            }else{
                erro("Dados inválidos, preencha corretamente!")
                dialog.cancel()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
    private fun pegaEmailUsuarioParaCheckIn() {

        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Dados Para Check-In")

        val email_editText = EditText(this)
        email_editText.hint = "Email"
        email_editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        builder.setView(email_editText)

        builder.setPositiveButton("OK") { dialog, _ ->
            val email = email_editText.text.toString()

            if(email.isNotEmpty()){
                fazCheckIn(email)
            }else{
                erro("Dados inválidos, preencha corretamente!")
                dialog.cancel()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun fazCheckIn(email: String) {
        showLoading()

        presenter.model.fazCheckIn(evento.id, nome, email)
    }
    fun sucessoCheckIn() {

        hideLoading()

        sucesso("Check-in feito com Sucesso! Não deixe de compartilhar o Evento. =D")

    }
    //endregion Check-in

    //region Utilitarios
    private fun carregaToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
    private fun sucesso(mensagem: String){

        hideLoading()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sucesso!")
        builder.setMessage(mensagem)
        builder.setPositiveButton("Ok"){ _, _ -> }
        builder.setIcon(R.drawable.ic_sucesso)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showLoading(){
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Fazendo Check-in...")
        progressDialog.show()
    }
    private fun hideLoading(){
        try {
            progressDialog.hide()
        }catch (ignored: java.lang.Exception){}
    }
    //endregion Utilitarios
}