package br.com.sicredi.sicredieventos

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


/**
 * Tela exibida rapidamente ao abrir o App com a logo do mesmo
 */
class TelaInicialDeCarregamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_inical_de_carregamento)

        configuraLayoutDaTela()

        criaTimerParaIrParaProximaTela()
    }

    private fun configuraLayoutDaTela() {
        //Deixa tela em full screen
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun criaTimerParaIrParaProximaTela() {
        val timer = object: CountDownTimer(20000, 1000){
            override fun onTick(millisUntilFinished: Long){}
            override fun onFinish() {

                chamaProximaTela()

            }
        }
        timer.start()
    }
    private fun chamaProximaTela(){
        Toast.makeText(this, "Próxima tela chamada", Toast.LENGTH_SHORT).show()
    }
}