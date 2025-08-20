package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.gymstra.adapters.rutinaExpandableAdapter
import com.example.gymstra.models.EjercicioModel
import com.example.gymstra.models.RutinaEjercicioModel
import com.example.gymstra.models.RutinaModel
import com.example.gymstra.services.RutinaService
import com.example.gymstra.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class rutinasDelAlumnoDetalle : AppCompatActivity() {

    lateinit var rutinaService: RutinaService
    lateinit var recyclerRutinas: RecyclerView
    lateinit var listaEjercicios: List<EjercicioModel>

    lateinit var tvNombreAlumnoA: TextView
    lateinit var tvNombreRutinaA: TextView
    lateinit var tvFechaRutinaA: TextView
    lateinit var imgPdf: ImageView
    lateinit var cancelar: Button
    lateinit var guardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rutinas_del_alumno_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de views
        val volver = findViewById<ImageView>(R.id.imgCerrarSesion6)
        recyclerRutinas = findViewById(R.id.recyclerViewRutinasAlumno)
        tvNombreAlumnoA = findViewById(R.id.tvNombreAlumnoA)
        tvNombreRutinaA = findViewById(R.id.tvNombreRutinaA)
        tvFechaRutinaA = findViewById(R.id.tvFechaRutinaA)
        imgPdf = findViewById(R.id.imgPdf)
        cancelar = findViewById(R.id.btnCancelar)
        guardar = findViewById(R.id.guardar)

        rutinaService = ServiceBuilder.buildService(RutinaService::class.java)

        // Inicializamos lista de ejercicios (puede venir de API en lugar de estático)
        listaEjercicios = listOf(
            EjercicioModel(1, "Remo a un brazo", 17),
            EjercicioModel(2, "Maquina de remo", 5),
            EjercicioModel(3, "Remo inclinado", 17)
        )

        // Mostrar nombre de la rutina
        tvNombreRutinaA.text = intent.getStringExtra("nombreRutina") ?: ""

        // Botones
        cancelar.setOnClickListener {
            startActivity(Intent(this, rutinasDelAlumno::class.java))
        }
        volver.setOnClickListener {
            startActivity(Intent(this, rutinasDelAlumno::class.java))
        }

        // Obtener rutinas y cargar RecyclerView
        obtenerRutinas()
    }

    private fun obtenerRutinas() {
        val call = rutinaService.getRutinas()
        call.enqueue(object : retrofit2.Callback<List<RutinaModel>> {
            override fun onResponse(
                call: Call<List<RutinaModel>>,
                response: Response<List<RutinaModel>>
            ) {
                if (response.isSuccessful) {
                    val rutinas = response.body() ?: emptyList()

                    recyclerRutinas.apply {
                        layoutManager = LinearLayoutManager(this@rutinasDelAlumnoDetalle)
                        adapter = rutinaExpandableAdapter(
                            rutinas.toMutableList(),
                            listaEjercicios.toMutableList()
                        )
                    }

                } else {
                    Toast.makeText(
                        this@rutinasDelAlumnoDetalle,
                        "Error al obtener las rutinas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<RutinaModel>>, t: Throwable) {
                Toast.makeText(
                    this@rutinasDelAlumnoDetalle,
                    "Error al obtener el listado de rutinas",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("Error", t.message.toString())
            }
        })
    }
}