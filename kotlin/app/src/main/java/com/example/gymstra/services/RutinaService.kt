package com.example.gymstra.services

import com.example.gymstra.models.RutinaModel
import retrofit2.Call
import retrofit2.http.GET

interface RutinaService {

    // Obtener todas las rutinas
    @GET("rutinas")
    fun getRutinas() : Call<List<RutinaModel>>

}