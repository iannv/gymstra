package com.example.gymstra.models

data class RutinaModel (
    val id_rutina : Int? = 0,
    val nombre : String,
    val series : Int,
    val repeticiones : Int,
    val ejercicio : List<Int>,
)