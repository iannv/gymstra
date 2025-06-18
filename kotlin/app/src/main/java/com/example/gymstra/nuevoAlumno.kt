package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
            val intent = Intent(this, alumnos::class.java)
            startActivity(intent)
        }
    }
}