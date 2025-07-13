package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.adapters.ejerciciosAdapter
import com.example.gymstra.models.EjercicioModel
import com.example.gymstra.services.EjercicioService
import com.example.gymstra.services.ServiceBuilder
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Response

class ejercicios : AppCompatActivity() {
    lateinit var ejercicioService: EjercicioService
    lateinit var recyclerViewEjercicios: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ejercicios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Adaptador
        recyclerViewEjercicios = findViewById(R.id.recyclerViewEjercicios)

        val btnNuevoEjercicio = findViewById<Button>(R.id.btnNuevoEjercicio)
        val volver = findViewById<ImageView>(R.id.volverEjercicio)

        volver.setOnClickListener {
            val intent = Intent(this, inicio::class.java)
            startActivity(intent)
        }

        btnNuevoEjercicio.setOnClickListener {
            val intent = Intent(this, nuevoEjercicio::class.java)
            startActivity(intent)
        }

        // Servicio
        ejercicioService = ServiceBuilder.buildService(EjercicioService::class.java)

        val call = ejercicioService.getEjercicios()
        call.enqueue(object : retrofit2.Callback<List<EjercicioModel>> {
            override fun onResponse(
                call: Call<List<EjercicioModel>>,
                response: Response<List<EjercicioModel>>
            ) {
                if (response.isSuccessful) {
                    recyclerViewEjercicios.apply {
                        layoutManager = LinearLayoutManager(this@ejercicios)
                        adapter = ejerciciosAdapter(response.body()!!)
                    }
                }
                else {
                    Toast.makeText(this@ejercicios, "Error al mostrar la lista de ejercicios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<EjercicioModel>>, t: Throwable) {
                Toast.makeText(this@ejercicios, "Error al obtener los ejercicios", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit", "ERROR: ${t.message}")
            }
        })


        // Eliminar un ejercicio
        //val callEliminar = ejercicioService.deleteEjercicio()


        // Chips



    }
}