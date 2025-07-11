package com.example.gymstra

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gymstra.models.AlumnoModel
import com.example.gymstra.models.nuevoAlumnoModel
import com.example.gymstra.services.AlumnoService
import com.example.gymstra.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class nuevoAlumno : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevo_alumno)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre = findViewById<EditText>(R.id.etNombreA)
        val apellido = findViewById<EditText>(R.id.etApellidoA)
        val dni = findViewById<EditText>(R.id.etDniA)
        val tel = findViewById<EditText>(R.id.etTelA)
        val vecesXsemana = findViewById<Spinner>(R.id.spinnerVecesXsemana)
        val guardar = findViewById<Button>(R.id.btnGuardar)
        val cancelar = findViewById<Button>(R.id.btnCancelar)
        val volver = findViewById<ImageView>(R.id.imgCerrarSesion3)

        nombre.requestFocus()

        volver.setOnClickListener {
            val intent = Intent(this, alumnos::class.java)
            startActivity(intent)
        }

        cancelar.setOnClickListener {
            val intent = Intent(this, alumnos::class.java)
            startActivity(intent)
        }

        guardar.setOnClickListener {
            val nuevoAlumno = nuevoAlumnoModel(
                dni = dni.text.toString(),
                nombre = nombre.text.toString(),
                apellido = apellido.text.toString(),
                telefono = tel.text.toString(),
                //vecesXsemana = vecesXsemana.selectedItem.toString().toInt(),
                idAdministrador = 1 // Despues cambiarlo para tomar el id del admin autenticado
            )

            val alumnoService = ServiceBuilder.buildService(AlumnoService::class.java)
            val call = alumnoService.addAlumno(nuevoAlumno)

            call.enqueue(object : retrofit2.Callback<AlumnoModel> {
                override fun onResponse(call: Call<AlumnoModel>, response: Response<AlumnoModel>) {

                    if (response.isSuccessful){
                        val alumnoCreado = response.body()
                        Toast.makeText(this@nuevoAlumno, "Se ha registrado un nuevo alumno", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@nuevoAlumno, alumnos::class.java)
                        startActivity(intent)
                    }
                    else {
                        Log.e("REGISTRO", "Código: ${response.code()}, Error: ${response.errorBody()?.string()}")
                        Toast.makeText(this@nuevoAlumno, "Error al registrar al alumno", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AlumnoModel>, t: Throwable) {
                    Toast.makeText(this@nuevoAlumno, "Error en el registro", Toast.LENGTH_SHORT).show()
                    Log.e("Retrofit", "ERROR: ${t.message}")
                }
            })


        }
    }
}