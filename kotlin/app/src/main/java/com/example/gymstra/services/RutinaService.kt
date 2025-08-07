package com.example.gymstra.services

import com.example.gymstra.models.RutinaModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RutinaService {

    // Obtener todas las rutinas
    @GET("rutinas")
    fun getRutinas() : Call<List<RutinaModel>>

    // Crear una rutina
    @POST("rutinas/")
    fun addRutina(@Body rutina: RutinaModel): Call<RutinaModel>
}