package br.com.sicredi.sicrediEventos.utilitarios

import androidx.annotation.Nullable

/** Cada número representa um erro específico no App.
* Útil para localizar erros no código, apenas tendo o código do número do erro,
* pois cada código de erro é referente a Apenas uma linha de código (Salvo pequenas exessões) */
class Erros {
    companion object {
        const val ERRO0 = 0
        const val ERRO1 = 1

        /** Gera uma mensagem de erro padrão
         * Ex:
        Erros.geraMensagemDeErro(
        "Erro ao ",
        ListaDeErros.ERROX,
        e
        )
         */
        fun geraMensagemDeErro(
            mensagemDeErro: String,
            codigoDoErro: Int,
            @Nullable exception: Exception?
        ): String {
            return if (exception != null) """
             $mensagemDeErro
                Contate a TI! Código do Erro: $codigoDoErro. ${exception.message}
             """.trimIndent()
            else """
             $mensagemDeErro
             Contate a TI! Código do Erro: $codigoDoErro. 
             """.trimIndent()
        }
    }
}