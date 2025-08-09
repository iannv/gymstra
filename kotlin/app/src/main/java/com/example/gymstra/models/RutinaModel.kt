package com.example.gymstra.models

data class RutinaModel (
    val id_rutina : Int? = 0,
    val nombre : String? = "",
    var series : Int,
    var repeticiones : Int,
    var ejercicio : List<Int> = emptyList(),
)

fun nuevaRutinaModel(series: Int, repeticiones: Int, ejercicio: List<Int>): RutinaModel {
    return RutinaModel(
        series = series,
        repeticiones = repeticiones,
        ejercicio = ejercicio,
    )
}