package com.example.gymstra.services

import com.example.gymstra.models.ZonaMuscularModel
import retrofit2.Call
import retrofit2.http.GET

interface ZonaMuscularService {

    // Obtener todas las zonas musuclares
    @GET("zona-muscular")
    fun getZonasMusculares(): Call<List<ZonaMuscularModel>>


    // Obtener una zona muscular por el ID
    @GET("zona-muscular/{id}/")
    fun getZonaMuscularId(): Call<ZonaMuscularModel>
}