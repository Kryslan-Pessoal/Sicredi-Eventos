package br.com.sicredi.sicrediEventos.utilitarios

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
    }
}