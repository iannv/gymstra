package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
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
import com.example.gymstra.adapters.alumnosAdapter
import com.example.gymstra.models.AlumnoModel
import com.example.gymstra.services.AlumnoService
import com.example.gymstra.services.ServiceBuilder
import retrofit2.Response

class alumnos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alumnos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnNuevoAlumo = findViewById<Button>(R.id.btnNuevoAlumno)
        val volver = findViewById<ImageView>(R.id.imgCerrarSesion2)

        // Adaptador alumnosAdapter
        val recyclerAlumnos = findViewById<RecyclerView>(R.id.recyclerAlumnos)

        btnNuevoAlumo.setOnClickListener {
            val intent = Intent(this, nuevoAlumno::class.java)
            startActivity(intent)
        }

        volver.setOnClickListener {
            val intent = Intent(this, inicio::class.java)
            startActivity(intent)
        }

        // Servicio
        val alumnoService = ServiceBuilder.buildService(AlumnoService::class.java)
        val call = alumnoService.getAlumnos()

        call.enqueue(object : retrofit2.Callback<List<AlumnoModel>> {
            override fun onResponse(
                call: retrofit2.Call<List<AlumnoModel>>,
                response: Response<List<AlumnoModel>>
            ) {
                if (response.isSuccessful) {
                    recyclerAlumnos.apply {
                        layoutManager = LinearLayoutManager(this@alumnos)
                        adapter = alumnosAdapter(response.body()!!)
                    }
                }
                else {
                    Toast.makeText(this@alumnos, "Error al mostrar la lista de alumnos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<AlumnoModel>>, t: Throwable) {
                Toast.makeText(this@alumnos, "Error al obtener los alumnos", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit", "ERROR: ${t.message}")
            }
        })
    }
}