package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
    lateinit var ejerciciosAdapter: ejerciciosAdapter
    lateinit var listaEjercicios: List<EjercicioModel>
    lateinit var filtrados: List<EjercicioModel>
    lateinit var nadaParaMostrarEjercicios: TextView

    lateinit var abdominalesChip: Chip
    lateinit var antebrazosChip: Chip
    lateinit var bicepsChip: Chip
    lateinit var cuadricepsChip: Chip
    lateinit var espaldaChip: Chip
    lateinit var gemelosChip: Chip
    lateinit var gluteosChip: Chip
    lateinit var hombrosChip: Chip
    lateinit var isquiotibialesChip: Chip
    lateinit var pechoChip: Chip
    lateinit var tricepsChip: Chip

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
        nadaParaMostrarEjercicios = findViewById(R.id.nadaParaMostrarEjercicios)

        val btnNuevoEjercicio = findViewById<Button>(R.id.btnNuevoEjercicio)
        val volver = findViewById<ImageView>(R.id.volverEjercicio)

        abdominalesChip =  findViewById(R.id.chip1)
        antebrazosChip = findViewById(R.id.chip2)
        bicepsChip = findViewById(R.id.chip3)
        cuadricepsChip = findViewById(R.id.chip4)
        espaldaChip = findViewById(R.id.chip5)
        gemelosChip = findViewById(R.id.chip6)
        gluteosChip = findViewById(R.id.chip7)
        hombrosChip = findViewById(R.id.chip8)
        isquiotibialesChip = findViewById(R.id.chip9)
        pechoChip = findViewById(R.id.chip10)
        tricepsChip =  findViewById(R.id.chip11)

        abdominalesChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 13 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        antebrazosChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 14 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        bicepsChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 15 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        cuadricepsChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 16 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        espaldaChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 17 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        gemelosChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 18 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        gluteosChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 19 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        hombrosChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 20 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        isquiotibialesChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 21 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        pechoChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 22 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }

        tricepsChip.setOnClickListener(){
            filtrados = listaEjercicios.filter { it.id_zona_muscular == 23 }
            ejerciciosAdapter.actualizarListaFiltrada(filtrados)
        }



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

        cargarListaEjercicios()
    }


    // Cargar listado de ejercicios
    private fun cargarListaEjercicios() {
        val call = ejercicioService.getEjercicios()
        call.enqueue(object : retrofit2.Callback<List<EjercicioModel>> {
            override fun onResponse(
                call: Call<List<EjercicioModel>>,
                response: Response<List<EjercicioModel>>
            ) {
                if (response.isSuccessful) {
                    listaEjercicios = response.body() ?: emptyList()

                    recyclerViewEjercicios.apply {
                        if (listaEjercicios.isEmpty()){
                            nadaParaMostrarEjercicios.visibility = View.VISIBLE
                            recyclerViewEjercicios.visibility = View.GONE
                        } else{
                            layoutManager = LinearLayoutManager(this@ejercicios)
                            ejerciciosAdapter = ejerciciosAdapter(listaEjercicios, this@ejercicios) { idEjercicio, nombreEjercicio ->
                                confirmarEliminarEjercicio(idEjercicio, nombreEjercicio)
                            }
                            recyclerViewEjercicios.adapter = ejerciciosAdapter
                        }

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
    }


    // Eliminar un ejercicio
    private fun eliminarEjercicio(idEjercicio: Int) {
        val call = ejercicioService.deleteEjercicio(idEjercicio)
        call.enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful){
                    cargarListaEjercicios()
                    Toast.makeText(this@ejercicios, "Ejercicio eliminado", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@ejercicios, "Error al eliminar el ejercicio", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@ejercicios, "Error al eliminar", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit", "ERROR: ${t.message}")
            }

        })
    }


    // Confirmación de eliminación
    private fun confirmarEliminarEjercicio(idEjercicio: Int, nombreEjercicio: String) {
        android.app.AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación de ${nombreEjercicio.uppercase()}")
            .setPositiveButton("Aceptar") { dialog, _ ->
                eliminarEjercicio(idEjercicio)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


}