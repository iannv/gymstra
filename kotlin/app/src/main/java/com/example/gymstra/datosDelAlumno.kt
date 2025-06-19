package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class datosDelAlumno : AppCompatActivity() {
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

    }
}