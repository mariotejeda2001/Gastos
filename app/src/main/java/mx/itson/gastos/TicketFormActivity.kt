package mx.itson.gastos

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
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
import kotlin.math.log

class TicketFormActivity : AppCompatActivity(), View.OnClickListener {

    // Declaramos el EditText a nivel de clase para acceder a él fácilmente
    private lateinit var etDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ticket_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnConfirm = findViewById<Button>(R.id.btn_confirm)
        etDate = findViewById(R.id.purchase_date)

        // Asignamos el evento click tanto al botón como al campo de fecha
        btnConfirm.setOnClickListener(this)
        etDate.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.purchase_date -> {
                // Obtenemos la fecha actual para que el calendario se abra en el día de hoy
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                //creamos y mostramos el dialogo del calendario
                val datePickerDialog = DatePickerDialog(this,{_, selectedYear, selectedMonth, selectedDay ->
                    // Nota técnica: Los meses en Calendar van de 0 a 11, por eso sumamos 1
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    // Escribimos la fecha formateada en el EditText
                    etDate.setText(formattedDate)
            },year,month,day)
                datePickerDialog.show()
            }

            R.id.btn_confirm -> {
                try {
                    val product = findViewById<EditText>(R.id.product_name).text.toString()
                    val price = findViewById<EditText>(R.id.product_price).text.toString()
                    val store = findViewById<EditText>(R.id.store_name).text.toString()
                    val date = etDate.text.toString()

                    //antes se usó este para tomar el del dispositivo val date = LocalDateTime.now().toString()

                    //Validar campos varios de primera
                    if(product.isBlank() || price.isBlank() || store.isBlank() || date.isBlank()){
                        Toast.makeText(this, getString(R.string.txt_cannot_empty_), Toast.LENGTH_SHORT).show()
                        return
                    }

                    val priceDouble = price.toDouble()

                    val compra = mx.itson.gastos.persisntence.Compra()
                    compra.save(this, product, priceDouble, store, date)

                    // Vibración
                    // Vibración con validación de versiones (Formato Alta Calidad)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        // 1. Para Android 12 (API 31) o superior
                        val vibratorAdmin = applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                        val vibrator = vibratorAdmin.defaultVibrator
                        vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE))
                        Log.d("VIBRACION", "Vibrando con VibratorManager (Android 12+)")

                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        // 2. Para Android 8.0 (API 26) hasta Android 11 (API 30)
                        val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE))
                        Log.d("VIBRACION", "Vibrando con VibrationEffect (Android 8 a 11)")

                    } else {
                        // 3. Para versiones de Android 7.1 (API 25) o menores
                        val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(1500)
                        Log.d("VIBRACION", "Vibrando con Vibrator clásico (Android 7 o menor)")
                    }

                    Toast.makeText(this, getString(R.string.txt_saved_correctly), Toast.LENGTH_SHORT).show()
                    finish()

                } catch(ex: Exception){
                    Toast.makeText(this,getString(R.string.txt_error_saving_ticket), Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}