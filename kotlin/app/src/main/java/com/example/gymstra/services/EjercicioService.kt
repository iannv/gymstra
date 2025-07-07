package com.example.gymstra.services

import com.example.gymstra.models.EjercicioModel
import retrofit2.Call
import retrofit2.http.GET

interface EjercicioService {

    // Obtener todos los ejercicios
    @GET("ejercicios")
    fun getEjercicios(): Call<List<EjercicioModel>>

}