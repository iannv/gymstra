package com.example.gymstra.models

data class EjercicioModel(
    val id_ejercicio : Int? = null,
    val nombre : String,
    val id_zona_muscular : Int,
)

fun nuevoEjercicioModel(nombre: String, id_zona_muscular: Int): EjercicioModel {
    return EjercicioModel(nombre = nombre, id_zona_muscular = id_zona_muscular)
}
