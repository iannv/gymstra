package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
import retrofit2.Callback
import retrofit2.Response

class nuevoEjercicio : AppCompatActivity() {

    lateinit var listaZonas: List<ZonaMuscularModel>
    lateinit var spinnerZonaMuscular: Spinner
    lateinit var ejercicioService: EjercicioService
    lateinit var nombre: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevo_ejercicio)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val accion = intent.getStringExtra("accion")
        val btnGuardar = findViewById<Button>(R.id.guardar)
        val btnCancelar = findViewById<Button>(R.id.cancelar)
        nombre = findViewById(R.id.etNombreE)
        spinnerZonaMuscular = findViewById(R.id.spinnerZonaMuscular)

        nombre.requestFocus()

        // Llenar el spinner con datos reales
        val zonasMuscularesService = ServiceBuilder.buildService(ZonaMuscularService::class.java)
        val callZonasMusculares = zonasMuscularesService.getZonasMusculares()


        callZonasMusculares.enqueue(object : retrofit2.Callback<List<ZonaMuscularModel>> {
            override fun onResponse(
                call: Call<List<ZonaMuscularModel>>,
                response: Response<List<ZonaMuscularModel>>
            ) {
                if (response.isSuccessful) {
                    listaZonas = response.body() ?: emptyList()
                    val nombreZonas = listaZonas.map { it.zona }

                    val adaptador = ArrayAdapter(this@nuevoEjercicio, R.layout.item_font_text, nombreZonas)
                    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerZonaMuscular.adapter = adaptador

                    if (accion == "editar") {
                        val nombreEjercicio = intent.getStringExtra("nombre")
                        val zonaMuscular = intent.getIntExtra("id_zona_muscular", 0)
                        nombre.setText(nombreEjercicio)

                        val indexZona = listaZonas.indexOfFirst { it.id_zona_muscular == zonaMuscular }
                        if (indexZona >= 0) {
                            spinnerZonaMuscular.setSelection(indexZona)
                        }
                    }

                } else {
                    Toast.makeText(this@nuevoEjercicio, "No se pudieron cargar zonas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ZonaMuscularModel>>, t: Throwable) {
                Toast.makeText(this@nuevoEjercicio, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit", "Fallo al cargar zonas musculares", t)
            }
        })




        btnGuardar.setOnClickListener {
            val zonaSeleccionada = listaZonas.getOrNull(spinnerZonaMuscular.selectedItemPosition)

            if (zonaSeleccionada != null) {
                val nuevoEjercicio = nuevoEjercicioModel(
                    nombre = nombre.text.toString(),
                    id_zona_muscular = zonaSeleccionada.id_zona_muscular
                )


                ejercicioService = ServiceBuilder.buildService(EjercicioService::class.java)

                // Verificar si es acción de edición o creación
                if (accion == "editar") {
                    val idEjercicio = intent.getIntExtra("id_ejercicio", 0)
                    val callEditar = ejercicioService.putEjercicio(idEjercicio, nuevoEjercicio)

                    callEditar.enqueue(object : Callback<EjercicioModel> {
                        override fun onResponse(call: Call<EjercicioModel>, response: Response<EjercicioModel>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@nuevoEjercicio, "Ejercicio editado", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@nuevoEjercicio, ejercicios::class.java))
                                finish()
                            } else {
                                Toast.makeText(this@nuevoEjercicio, "Error al editar: ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<EjercicioModel>, t: Throwable) {
                            Log.e("Retrofit", "Error: ${t.message}")
                            Toast.makeText(this@nuevoEjercicio, "Fallo al editar ejercicio", Toast.LENGTH_SHORT).show()
                        }
                    })

                } else {
                    val callCrear = ejercicioService.addEjercicio(nuevoEjercicio)

                    callCrear.enqueue(object : Callback<EjercicioModel> {
                        override fun onResponse(call: Call<EjercicioModel>, response: Response<EjercicioModel>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@nuevoEjercicio, "Ejercicio agregado", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@nuevoEjercicio, ejercicios::class.java))
                                finish()
                            } else {
                                Toast.makeText(this@nuevoEjercicio, "Error al agregar: ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<EjercicioModel>, t: Throwable) {
                            Log.e("Retrofit", "Error: ${t.message}")
                            Toast.makeText(this@nuevoEjercicio, "Fallo al agregar ejercicio", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

            } else {
                Toast.makeText(this, "Seleccioná una zona válida", Toast.LENGTH_SHORT).show()
            }
        }



        // Cancelar y volver
        btnCancelar.setOnClickListener {
            val intent = Intent(this, ejercicios::class.java)
            startActivity(intent)
            finish()
        }
    }
}
