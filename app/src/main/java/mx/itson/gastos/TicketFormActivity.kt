package mx.itson.gastos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        btnConfirm.setOnClickListener {this}
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_confirm -> {
                try {
                    val product = findViewById<EditText>(R.id.product_name).text.toString()
                    val price = findViewById<EditText>(R.id.product_price).text.toString()
                    val store = findViewById<EditText>(R.id.store_name).text.toString()
                    val date = LocalDateTime.now().toString()
                    val intentTicketForm = Intent(this, MainActivity::class.java)
                    startActivity(intentTicketForm)

                    if (product.isBlank()||price.isBlank()||store.isBlank()){
                        Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                        return
                    }

                    /*Aquí iría la parte de la entidad, solo hay que revisar que coincidan
                    los nombres y parametros
                     */

                    //Ticket().save(this,product,price,store,date)

                } catch(ex: Exception){
                    Toast.makeText(this,"Error al guardar el ticket", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}