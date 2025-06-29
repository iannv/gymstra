package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.adapters.ejerciciosAdapter

class ejercicios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ejercicios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Adaptador ejerciciosAdapter
        val recyclerViewEjercicios = findViewById<RecyclerView>(R.id.recyclerViewEjercicios)
        recyclerViewEjercicios.layoutManager = LinearLayoutManager(this)
        recyclerViewEjercicios.adapter = ejerciciosAdapter()

        val nuevoEjercicio = findViewById<Button>(R.id.btnNuevoEjercicio)
        val volver = findViewById<ImageView>(R.id.volverEjercicio)

        volver.setOnClickListener {
            val intent = Intent(this, inicio::class.java)
            startActivity(intent)
        }

        nuevoEjercicio.setOnClickListener {
            val intent = Intent(this, nuevoEjercicio::class.java)
            startActivity(intent)
        }

        // Chips



    }
}