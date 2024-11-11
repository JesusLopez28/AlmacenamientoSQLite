package com.example.almacenamientosqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GuardabosqueDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "GuardabosquesDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_GUARDABOSQUES = "Guardabosques"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_EDAD = "edad"
        private const val COLUMN_ANIOS_SERVICIO = "anios_servicio"
        private const val COLUMN_ESPECIALIDAD = "especialidad"
        private const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_GUARDABOSQUES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_EDAD INTEGER," +
                "$COLUMN_ANIOS_SERVICIO INTEGER," +
                "$COLUMN_ESPECIALIDAD TEXT," +
                "$COLUMN_DESCRIPCION TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GUARDABOSQUES")
        onCreate(db)
    }

    fun insertarGuardabosque(
        nombre: String,
        edad: Int,
        aniosServicio: Int,
        especialidad: String,
        descripcion: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_EDAD, edad)
            put(COLUMN_ANIOS_SERVICIO, aniosServicio)
            put(COLUMN_ESPECIALIDAD, especialidad)
            put(COLUMN_DESCRIPCION, descripcion)
        }
        return db.insert(TABLE_GUARDABOSQUES, null, values)
    }

    fun buscarGuardabosque(nombre: String): Cursor? {
        val db = this.readableDatabase
        return db.query(
            TABLE_GUARDABOSQUES,
            null,
            "$COLUMN_NOMBRE=?",
            arrayOf(nombre),
            null,
            null,
            null
        )
    }

    fun actualizarGuardabosque(
        id: Int,
        nombre: String,
        edad: Int,
        aniosServicio: Int,
        especialidad: String,
        descripcion: String
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_EDAD, edad)
            put(COLUMN_ANIOS_SERVICIO, aniosServicio)
            put(COLUMN_ESPECIALIDAD, especialidad)
            put(COLUMN_DESCRIPCION, descripcion)
        }
        return db.update(TABLE_GUARDABOSQUES, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun eliminarGuardabosque(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_GUARDABOSQUES, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun obtenerGuardabosques(): List<Guardabosque> {
        val guardabosques = mutableListOf<Guardabosque>()
        val db = readableDatabase
        val TABLE_NAME = "Guardabosques"
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val guardabosque = Guardabosque(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    edad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EDAD)),
                    aniosServicio = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIOS_SERVICIO)),
                    especialidad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESPECIALIDAD)),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                )
                guardabosques.add(guardabosque)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return guardabosques
    }

}
