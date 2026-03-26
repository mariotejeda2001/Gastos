package mx.itson.gastos.persisntence

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
class Compra {
    var id = 0
    var producto: String = ""
    var precio: Double = 0.0
    var tienda: String = ""
    var fecha: String = ""

    constructor()
    constructor(id: Int, producto: String, precio: Double, tienda: String, fecha: String) {
        this.id = id
        this.producto = producto
        this.precio = precio
        this.tienda = tienda
        this.fecha = fecha
    }

    fun save(context: Context, producto: String, precio: Double, tienda: String, fecha: String) {
        try {

            val gastosDB = GastosDB(context, "GastosDB", null, 1)
            // constante con control a base de datos
            val dataBase: SQLiteDatabase = gastosDB.writableDatabase
            val values = ContentValues()
            values.put("producto", producto)
            values.put("precio", precio)
            values.put("tienda", tienda)
            values.put("fecha", fecha)

            dataBase.insert("Compras", null, values)

        } catch (ex: Exception) {
            Log.d("Error saving compra", ex.message.toString())
        }
    }

    fun list(context: Context): List<Compra> {
        // variable donde definimos el tipo de valor como lista mutable para filas nuevas en la base de datos
        var compras: MutableList<Compra> = ArrayList()
        try {
            val gastosDB = GastosDB(context, "GastosDB", null, 1)
            // constante con control a base de datos
            val dataBase: SQLiteDatabase = gastosDB.readableDatabase
            val resultSet = dataBase.rawQuery("SELECT id, producto, precio, tienda, fecha FROM Compras", null)

            while (resultSet.moveToNext()) {
                val compra = Compra(
                    resultSet.getInt(0),
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
                )
                compras.add(compra)
            }

        } catch (ex: Exception) {
            Log.d("Error getting compra", ex.message.toString())
        }
        return compras
    }
}