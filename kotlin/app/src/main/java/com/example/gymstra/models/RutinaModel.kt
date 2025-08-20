package com.example.gymstra.models

data class RutinaModel(
    val id_rutina: Int? = 0,
    val nombre: String? = "",
    val ejercicios: List<RutinaEjercicioModel>
)