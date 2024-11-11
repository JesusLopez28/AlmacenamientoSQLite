package com.example.almacenamientosqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ParqueDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ParquesDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PARQUES = "Parques"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_UBICACION = "ubicacion"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_TAMANO = "tamano"
        private const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_PARQUES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_UBICACION TEXT," +
                "$COLUMN_TIPO TEXT," +
                "$COLUMN_TAMANO INTEGER," +
                "$COLUMN_DESCRIPCION TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARQUES")
        onCreate(db)
    }

    fun insertarParque(
        nombre: String,
        ubicacion: String,
        tipo: String,
        tamano: Int,
        descripcion: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_UBICACION, ubicacion)
            put(COLUMN_TIPO, tipo)
            put(COLUMN_TAMANO, tamano)
            put(COLUMN_DESCRIPCION, descripcion)
        }
        return db.insert(TABLE_PARQUES, null, values)
    }

    fun buscarParque(nombre: String): Cursor? {
        val db = this.readableDatabase
        return db.query(TABLE_PARQUES, null, "$COLUMN_NOMBRE=?", arrayOf(nombre), null, null, null)
    }

    fun actualizarParque(
        id: Int,
        nombre: String,
        ubicacion: String,
        tipo: String,
        tamano: Int,
        descripcion: String
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_UBICACION, ubicacion)
            put(COLUMN_TIPO, tipo)
            put(COLUMN_TAMANO, tamano)
            put(COLUMN_DESCRIPCION, descripcion)
        }
        return db.update(TABLE_PARQUES, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun eliminarParque(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_PARQUES, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun obtenerParques(): List<Parque> {
        val parques = mutableListOf<Parque>()
        val db = readableDatabase
        val TABLE_NAME = "Parques"
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val parque = Parque(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    ubicacion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UBICACION)),
                    tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO)),
                    tamano = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TAMANO)),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                )
                parques.add(parque)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return parques
    }
}
