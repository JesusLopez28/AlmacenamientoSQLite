package com.example.almacenamientosqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Parques (id INTEGER PRIMARY KEY, nombre TEXT, ubicacion TEXT, tipo TEXT, tamano TEXT, descripcion TEXT)")
        db.execSQL("CREATE TABLE Guardabosques (id INTEGER PRIMARY KEY, nombre TEXT, edad INTEGER, anios_servicio INTEGER, especialidad TEXT, descripcion TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Parques")
        db.execSQL("DROP TABLE IF EXISTS Guardabosques")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "appDatabase.db"
        private const val DATABASE_VERSION = 1
    }
}
