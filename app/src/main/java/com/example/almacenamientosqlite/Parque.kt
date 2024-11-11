package com.example.almacenamientosqlite

data class Parque(
    val id: Int = 0,
    val nombre: String,
    val ubicacion: String,
    val tipo: String,
    val tamano: Int,
    val descripcion: String
)