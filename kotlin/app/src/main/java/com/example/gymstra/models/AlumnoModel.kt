package com.example.gymstra.models

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class AlumnoModel(
    val id_alumno : Int? = null,
    var dni : String,
    var nombre : String,
    var apellido : String,
    var telefono : String,
    val fecha_ingreso : String,
    var vecesXsemana : Int,
    val fecha_ultimo_dia : String,
    val activo : Boolean,
    val id_administrador : Int,
    val clase : List<Int> = emptyList(),
    val rutina : List<Int>,
)

fun nuevoAlumnoModel(
    dni: String,
    nombre: String,
    apellido: String,
    telefono: String,
    vecesXsemana: Int,
    idAdministrador: Int
): AlumnoModel {
    val fecha = Calendar.getInstance()
    val fechaFormato = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val fechaHoy = fechaFormato.format(fecha.time)

    return AlumnoModel(
        dni = dni,
        nombre = nombre,
        apellido = apellido,
        telefono = telefono,
        fecha_ingreso = fechaHoy,
        vecesXsemana = vecesXsemana,
        fecha_ultimo_dia = fechaHoy,
        rutina = listOf(),
        activo = true,
        id_administrador = idAdministrador
    )
}

