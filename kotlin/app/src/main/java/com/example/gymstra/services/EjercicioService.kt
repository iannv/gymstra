package com.example.gymstra.services

import retrofit2.Call
import retrofit2.http.GET

interface EjercicioService {

    // Obtener todos los ejercicios
    @GET("ejercicios")
    fun getEjercicios(): Call<List<EjercicioService>>

}