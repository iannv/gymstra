package com.example.gymstra.services

import android.telecom.Call
import com.example.gymstra.models.AlumnoModel
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AlumnoService {

    // ************ ALUMNOS ************
    // Obtener todos los alumnos
    @GET("alumnos")
    fun getAlumnos(): retrofit2.Call<List<AlumnoModel>>


    // Obtener alumno por ID
    @GET("alumnos/{id}/")
    fun getAlumno(@Path("id") id: Int): retrofit2.Call<AlumnoModel>


    // Registrar alumno
    @POST("alumnos/")
    fun addAlumno(@Body alumno: AlumnoModel): retrofit2.Call<AlumnoModel>


    // Editar alumno
    @PUT("alumnos/{id}/")
    fun putAlumno(@Path("id") id: Int, @Body alumno: AlumnoModel): retrofit2.Call<AlumnoModel>


    // Eliminar alumno
    @DELETE("alumnos/{id}/")
    fun deleteAlumno(@Path("id") id: Int): retrofit2.Call<Unit>

}

