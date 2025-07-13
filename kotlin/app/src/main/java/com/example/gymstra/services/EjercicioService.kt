package com.example.gymstra.services

import com.example.gymstra.models.EjercicioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EjercicioService {

    // Obtener todos los ejercicios
    @GET("ejercicios")
    fun getEjercicios(): Call<List<EjercicioModel>>


    // Registrar ejercicio
    @POST("ejercicios/")
    fun addEjercicio(@Body ejercicio: EjercicioModel): Call<EjercicioModel>


    // Eliminar ejercicio
    @DELETE("ejercicios/{id}/")
    fun deleteEjercicio(@Path("id") id: Int): Call<Unit>

}