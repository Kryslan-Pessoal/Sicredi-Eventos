package br.com.sicredi.sicrediEventos

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import br.com.sicredi.sicrediEventos.eventos.view.EventosView
import br.com.sicredi.sicredieventos.R


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
        val timer = object: CountDownTimer(1000, 1000){
            override fun onTick(millisUntilFinished: Long){}
            override fun onFinish() {

                chamaProximaTela()

            }
        }
        timer.start()
    }
    private fun chamaProximaTela(){
        val intent = Intent(this, EventosView::class.java).apply {}
        startActivity(intent)
        finish()
    }
}