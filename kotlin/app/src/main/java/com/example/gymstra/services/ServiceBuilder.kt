package com.example.gymstra.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    // Ruta de la API
    private const val URL = "http://10.0.2.2:8000/"

    // Crear el cliente OkHttp
    private val okHttp = OkHttpClient.Builder()

    // Crear el retrofit builder
    private val builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // Crear la instancia de retrofit
    private val retrofit = builder.build()
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}

