package mx.itson.gastos

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.gastos.persisntence.Compra

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // estos test se pueden borrar si gustan
        //test para guardado de datos
        val compra = Compra()
        compra.save(this, "manzanas", 25.50, "LEY", "23/03/2026")
        // test para lectura de datos
        val listaCompras = Compra().list(this)
        for (c in listaCompras) {
            Log.d("PRUEBA_BD", "ID: ${c.id} | Producto: ${c.producto} | Tienda: ${c.tienda} | Precio: $${c.precio}")
        }
    }
}