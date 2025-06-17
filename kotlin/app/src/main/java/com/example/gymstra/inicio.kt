package com.example.gymstra

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        val cerrarSesion = findViewById<ImageView>(R.id.imgCerrarSesion)
        val imgAlumnos = findViewById<ImageView>(R.id.imgAlumnos)
        val imgEjercicios = findViewById<ImageView>(R.id.imgEjercicios)
        val imgRutinas = findViewById<ImageView>(R.id.imgRutina)

        // Menú
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val nombre = "Ian"
        tvBienvenida.text = "Hola ${nombre},\n¡Bienvenido de nuevo!"


        val imagenA = findViewById<ImageView>(R.id.imgAlumnos)
        imagenA.setColorFilter(Color.parseColor("#AA323954"))
        val imagenE = findViewById<ImageView>(R.id.imgEjercicios)
        imagenE.setColorFilter(Color.parseColor("#AA323954"))
        val imagenR = findViewById<ImageView>(R.id.imgRutina)
        imagenR.setColorFilter(Color.parseColor("#AA323954"))

        imgAlumnos.setOnClickListener {
            val intent = Intent(this, alumnos::class.java)
            startActivity(intent)
        }

        imgEjercicios.setOnClickListener {
            val intent = Intent(this, ejercicios::class.java)
            startActivity(intent)
        }

        imgRutinas.setOnClickListener {
            val intent = Intent(this, rutinas::class.java)
            startActivity(intent)
        }

        cerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}