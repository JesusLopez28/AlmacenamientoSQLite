package com.example.almacenamientosqlite

data class Guardabosque(
    val id: Int = 0,
    val nombre: String,
    val edad: Int,
    val aniosServicio: Int,
    val especialidad: String,
    val descripcion: String
)