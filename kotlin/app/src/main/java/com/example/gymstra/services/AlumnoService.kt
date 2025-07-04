package com.example.gymstra.services

import android.telecom.Call
import com.example.gymstra.models.AlumnoModel
import retrofit2.Response
import retrofit2.http.GET

interface AlumnoService {

    // ************ ALUMNOS ************
    // Obtener todos los alumnos
    @GET("alumnos")
    fun getAlumnos(): retrofit2.Call<List<AlumnoModel>>

    // Obtener alumno por ID

}

