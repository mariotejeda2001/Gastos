package mx.itson.gastos

import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.gastos.persisntence.Compra
import java.time.LocalDateTime

class TicketFormActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ticket_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnConfirm = findViewById<View>(R.id.btn_confirm) as Button
        btnConfirm.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_confirm -> {
                try {
                    val product = findViewById<EditText>(R.id.product_name).text.toString()
                    val price = findViewById<EditText>(R.id.product_price).text.toString()
                    val store = findViewById<EditText>(R.id.store_name).text.toString()
                    val date = LocalDateTime.now().toString()

                    //Validar campos varios de primera
                    if(product.isBlank() || price.isBlank() || store.isBlank()){
                        Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                        return
                    }

                    //Guardar en la DB
                    val priceDouble = price.toDouble()
                    val compra = Compra()
                    compra.save(this, product, priceDouble, store, date)

                    //Vibrador
                    val vibrador = getSystemService(VIBRATOR_SERVICE) as android.os.Vibrator
                    if (vibrador.hasVibrator()){
                        vibrador.vibrate(android.os.VibrationEffect.createOneShot(200, android.os.VibrationEffect.DEFAULT_AMPLITUDE))
                    }
                    Toast.makeText(this,"Gasto guardado correctamente", Toast.LENGTH_SHORT).show()

                    //regresar a la pagina principal
                    finish()

                } catch(ex: Exception){
                    Toast.makeText(this,"Error al guardar el ticket", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}