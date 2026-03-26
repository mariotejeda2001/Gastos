package mx.itson.gastos.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.itson.gastos.R
import mx.itson.gastos.persisntence.Compra

class CompraAdapter(context: Context, compras: List<Compra>): BaseAdapter() {

    // 1. Declaración de variables tal como lo hace el profesor
    var context: Context = context
    var compraList: List<Compra> = compras

    override fun getCount(): Int {
        return compraList.size
    }

    override fun getItem(position: Int): Any {
        return compraList[position]
    }

    // 2. Retornar 0 fijo como en el ejemplo
    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        // 3. Inflar el layout directamente sin reutilizar convertView
        val element = LayoutInflater.from(context).inflate(R.layout.item_compra, null)

        // 4. Bloque try-catch para proteger la vista
        try {
            val compra = getItem(position) as Compra

            val txtProducto: TextView = element.findViewById(R.id.tv_producto)
            txtProducto.text = compra.producto

            val txtTiendaFecha: TextView = element.findViewById(R.id.tv_tienda_fecha)
            // Concatenamos tienda y fecha para aprovechar el diseño
            txtTiendaFecha.text = "${compra.tienda} - ${compra.fecha}"

            val txtPrecio: TextView = element.findViewById(R.id.tv_precio)
            // Agregamos el signo de pesos y mostramos el precio
            txtPrecio.text = "$${compra.precio}"

        } catch (ex: Exception){
            Log.e("Error showing Compras", ex.message.toString())
        }

        return element
    }
}