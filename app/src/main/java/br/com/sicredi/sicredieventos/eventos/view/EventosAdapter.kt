package br.com.sicredi.sicredieventos.eventos.view

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.sicredi.sicredieventos.R
import br.com.sicredi.sicredieventos.entidades.Evento
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class EventosAdapter (
    private val context: Activity,
    private val eventos: ArrayList<Evento>
) : ArrayAdapter<Evento>(context, R.layout.list_evento, eventos) {

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //Carrega Inflater e View
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_evento, null)

        //Carrega itens da tela
        val logoEvento_imageView: ImageView = view.findViewById(R.id.logoEvento_imageView)
        val tituloEvento_TextView: TextView = view.findViewById(R.id.tituloEvento_TextView)
        val descricaoEvento_textView: TextView = view.findViewById(R.id.descricaoEvento_textView)
        val dataEvento_TextView: TextView = view.findViewById(R.id.dataEvento_TextView)
        val precoEvento_textView: TextView = view.findViewById(R.id.precoEvento_textView)

        //Carrega Imagem
        Picasso.get().load(eventos[position].image).into(logoEvento_imageView, object: Callback {
            override fun onSuccess() {
                Picasso.get().load(eventos[position].image).into(logoEvento_imageView)
            }
            override fun onError(e: Exception?) {
                Picasso.get().load(R.drawable.sicredi_eventos_logo).into(logoEvento_imageView)
            }
        })

        //Carrega os textos
        tituloEvento_TextView.text = eventos[position].title
        descricaoEvento_textView.text = eventos[position].description
        //TODO: converter a data em formato normal
        dataEvento_TextView.text = "08/02/2022"//eventos[position].date
        precoEvento_textView.text = converteDoubleEmMoeda(eventos[position].price)

        return view
    }

    private fun converteDoubleEmMoeda(valor: Double): String{
        return try {
            var valorConvertido: String = "R$ " + valor.toBigDecimal().toPlainString()
            if(valorConvertido.indexOf('.') != -1)
                valorConvertido = valorConvertido.replace('.', ',')

            valorConvertido
        } catch (ignored: Exception) {
            "R$ 0,00"
        }
    }
}