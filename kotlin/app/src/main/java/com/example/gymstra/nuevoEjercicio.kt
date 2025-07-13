package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gymstra.models.EjercicioModel
import com.example.gymstra.models.ZonaMuscularModel
import com.example.gymstra.models.nuevoEjercicioModel
import com.example.gymstra.services.EjercicioService
import com.example.gymstra.services.ServiceBuilder
import com.example.gymstra.services.ZonaMuscularService
import retrofit2.Call
import retrofit2.Response

class nuevoEjercicio : AppCompatActivity() {
    lateinit var ejercicioService: EjercicioService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevo_ejercicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre = findViewById<EditText>(R.id.etNombreE)
        val btnGuardar = findViewById<Button>(R.id.guardar)
        val btnCancelar = findViewById<Button>(R.id.cancelar)

        nombre.requestFocus()

        // Servicio para obtener las zonas musculares en el spinner
        val spinnerZonaMuscular = findViewById<Spinner>(R.id.spinnerZonaMuscular)
        val zonasMuscularesService = ServiceBuilder.buildService(ZonaMuscularService::class.java)
        val callZonasMusculares = zonasMuscularesService.getZonasMusculares()

        callZonasMusculares.enqueue(object : retrofit2.Callback<List<ZonaMuscularModel>> {
            override fun onResponse(
                call: Call<List<ZonaMuscularModel>>,
                response: Response<List<ZonaMuscularModel>>
            ) {
                if (response.isSuccessful){
                    val zonasMusculares = response.body() ?: emptyList()
                    val nombreZonas = zonasMusculares.map { it.zona }
                    val adaptador = ArrayAdapter(this@nuevoEjercicio, android.R.layout.simple_spinner_item, nombreZonas)
                    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerZonaMuscular.adapter = adaptador

                    spinnerZonaMuscular.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val selectedItem = nombreZonas[position]
                            Toast.makeText(this@nuevoEjercicio, "Seleccionado: $selectedItem", Toast.LENGTH_SHORT).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                }
            }

            override fun onFailure(call: Call<List<ZonaMuscularModel>>, t: Throwable) {
                Toast.makeText(this@nuevoEjercicio, "Error al cargar zonas musculares", Toast.LENGTH_SHORT).show()
            }

        })



        // Guardar el ejercicio
        btnGuardar.setOnClickListener {
            val nuevoEjercicioModel = nuevoEjercicioModel(
                nombre = nombre.text.toString(),
                id_zona_muscular = spinnerZonaMuscular.selectedItem.toString().toInt()
            )

            val ejercicioService = ServiceBuilder.buildService(EjercicioService::class.java)
            val callNuevoEjercicio = ejercicioService.addEjercicio(nuevoEjercicioModel)

            callNuevoEjercicio.enqueue(object : retrofit2.Callback<EjercicioModel> {
                override fun onResponse(
                    call: Call<EjercicioModel>,
                    response: Response<EjercicioModel>
                ) {
                    if (response.isSuccessful){
                        val ejercicioCreado = response.body()
                        val intent = Intent(this@nuevoEjercicio, ejercicios::class.java)
                        startActivity(intent)
                        Toast.makeText(this@nuevoEjercicio, "Ejercicio agregado", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<EjercicioModel>, t: Throwable) {
                    Toast.makeText(this@nuevoEjercicio, "Error al agregar el ejerccio", Toast.LENGTH_SHORT).show()
                    Log.e("Retrofit", "ERROR: ${t.message}")
                }
            })
        }


        // Cancelar
        btnCancelar.setOnClickListener {
            val intent = Intent(this, ejercicios::class.java)
            startActivity(intent)
        }
    }
}

// TODO: NO SE GUARDA EL EJERCICIO EN EL BTN DE GUARDAR DE LA ACTIVITY DEL NUEVO EJERCICIO