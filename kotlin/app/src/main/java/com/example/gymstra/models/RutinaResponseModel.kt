package com.example.gymstra.models

data class RutinaResponseModel(
    val id_rutina: Int,
    val nombre: String,
    val ejercicios: List<Int>
)
