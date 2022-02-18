package br.com.sicredi.sicrediEventos.utilitarios

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object{
        fun converteDoubleEmMoeda(valor: Double): String{
            return try {
                var valorConvertido: String = "R$ " + valor.toBigDecimal().toPlainString()
                if(valorConvertido.indexOf('.') != -1)
                    valorConvertido = valorConvertido.replace('.', ',')

                val qtdCaracteresAposAVirgula = valorConvertido.substring(valorConvertido.indexOf(',') + 1).length
                if(qtdCaracteresAposAVirgula < 2)
                    valorConvertido += "0"


                valorConvertido
            } catch (ignored: Exception) {
                "R$ 0,00"
            }
        }
        @SuppressLint("SimpleDateFormat")
        fun converteMilisegundosEmData(ms: Long): String? {
            val c = Calendar.getInstance()
            c[Calendar.HOUR] = 0
            c[Calendar.MINUTE] = 0
            c[Calendar.SECOND] = 0
            c[Calendar.MILLISECOND] = 0
            c.add(Calendar.MILLISECOND, ms.toInt())
            return SimpleDateFormat("dd/MM/yyyy HH:mm").format(c.time)
        }
    }
}