package mx.itson.gastos.persisntence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class GastosDB(context: Context,
               name: String,
               factory: SQLiteDatabase.CursorFactory?,
               version: Int,
    ) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("CREATE TABLE Compras (id INTEGER PRIMARY KEY AUTOINCREMENT, producto TEXT, precio REAL, tienda TEXT, fecha TEXT)")
        }catch (ex: Exception){
            Log.d("Error al cargar la base de datos", ex.message.toString())
        }
    }

    override fun onUpgrade(
        p0: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }
}