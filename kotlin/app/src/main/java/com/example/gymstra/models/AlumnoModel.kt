package com.example.gymstra.models

import java.util.Date

data class AlumnoModel(
    val id_alumno : Int? = 0,
    val dni : String,
    val nombre : String,
    val apellido : String,
    val telefono : String,
    val fecha_ingreso : String,
    val vecesXsemana : Int,
    val fecha_ultimo_dia : String,
    val activo : Boolean,
    val id_administrador : Int,
    val clase : List<Int>,
    val rutina : List<Int>,
)
