package com.example.gymstra.models

data class RutinaEjercicioModel(
    var id_rutina_ejercicio: Int? = null,
    var ejercicio: EjercicioModel? = null,
    var series: Int = 0,
    var repeticiones: List<Int>
)
