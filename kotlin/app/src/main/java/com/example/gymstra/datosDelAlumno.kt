package com.example.gymstra

import android.content.Intent
import android.icu.number.UnlocalizedNumberFormatter
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
import com.example.gymstra.models.AlumnoModel
import com.example.gymstra.services.AlumnoService
import com.example.gymstra.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class datosDelAlumno : AppCompatActivity() {
    lateinit var alumnoService: AlumnoService
    lateinit var alumnoModel: AlumnoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_del_alumno)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val volver = findViewById<ImageView>(R.id.imgCerrarSesion4)
        val volver2 = findViewById<Button>(R.id.btnVolver)
        val btnVerRutina2 = findViewById<Button>(R.id.btnVerRutina2)


        volver.setOnClickListener {
            val intent = Intent(this, alumnos::class.java)
            startActivity(intent)
        }

        volver2.setOnClickListener {
            val intent = Intent(this, alumnos::class.java)
            startActivity(intent)
        }

        btnVerRutina2.setOnClickListener {
            val intent = Intent(this, rutinasDelAlumno::class.java)
            startActivity(intent)
        }

        getDatosAlumno()
    }


    // Obtener los datos de un alumno
    private fun getDatosAlumno(){
        val idAlumno = intent.getIntExtra("idAlumno", -1)
        alumnoService = ServiceBuilder.buildService(AlumnoService::class.java)
        val call = alumnoService.getAlumno(idAlumno)

        call.enqueue(object : Callback<AlumnoModel> {
            override fun onResponse(call: Call<AlumnoModel>, response: Response<AlumnoModel>) {
                if (response.isSuccessful){
                    val alumno = response.body()
                    if (alumno != null){
                        findViewById<TextView>(R.id.tvIngresoA).text = alumno.fecha_ingreso
                        findViewById<TextView>(R.id.tvNombreCompletoA).text = alumno.nombre
                        findViewById<TextView>(R.id.tvDniA).text = alumno.dni
                        findViewById<TextView>(R.id.tvTelA).text = alumno.telefono
                        findViewById<TextView>(R.id.tvVecesPorSemanaA).text = alumno.vecesXsemana.toString()
                    }
                } else {
                    Toast.makeText(this@datosDelAlumno, "Error al obtener los datos del alumno", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AlumnoModel>, t: Throwable) {
                Toast.makeText(this@datosDelAlumno, "Error de conexión", Toast.LENGTH_SHORT).show()
                Log.e("Error retrofit", t.message.toString())
            }
        })
    }

}


