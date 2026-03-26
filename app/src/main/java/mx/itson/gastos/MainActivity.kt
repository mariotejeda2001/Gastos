package mx.itson.gastos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mx.itson.gastos.adapter.CompraAdapter
import mx.itson.gastos.persisntence.Compra

class MainActivity : AppCompatActivity() {

    private lateinit var lvCompras: ListView
    private lateinit var fabAddGasto: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Inicializamos las vistas
        lvCompras = findViewById(R.id.lv_compras)
        fabAddGasto = findViewById(R.id.fab_add_gasto)

        // 2. Programamos el botón flotante para ir al formulario
        fabAddGasto.setOnClickListener {
            val intentTicketForm = Intent(this, TicketFormActivity::class.java)
            startActivity(intentTicketForm)

        }
    }
    // Es vital cargar la lista en onResume, para que se actualice
    // cuando regresemos del formulario de guardado.
    override fun onResume() {
        super.onResume()
        cargarLista()
    }

    private fun cargarLista() {
        // 3. Consultamos la base de datos a través de la entidad Compra
        val listaCompras = Compra().list(this)

        // 4. Instanciamos nuestro adaptador personalizado con los datos
        val adapter = CompraAdapter(this, listaCompras)

        // 5. Asignamos el adaptador al ListView
        lvCompras.adapter = adapter
    }
}