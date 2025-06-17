package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val btnRegistrarme = findViewById<Button>(R.id.btnRegistrarme)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etClave = findViewById<EditText>(R.id.etClave)

        etEmail.requestFocus()

        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, inicio::class.java)
            startActivity(intent)
        }

        btnRegistrarme.setOnClickListener {
            val intent = Intent(this, registrarse::class.java)
            startActivity(intent)
        }


    }
}