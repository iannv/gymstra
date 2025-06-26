package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.adapters.alumnosAdapter

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
        recyclerAlumnos.layoutManager = LinearLayoutManager(this)
        recyclerAlumnos.adapter = alumnosAdapter()


        btnNuevoAlumo.setOnClickListener {
            val intent = Intent(this, nuevoAlumno::class.java)
            startActivity(intent)
        }

        volver.setOnClickListener {
            val intent = Intent(this, inicio::class.java)
            startActivity(intent)
        }

    }
}