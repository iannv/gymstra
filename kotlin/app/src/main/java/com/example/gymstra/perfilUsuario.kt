package com.example.gymstra

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class perfilUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        /*
        APRETAR EL ICONO DEL OJO PARA VER LA CONTRASEÑA (ARREGLAR EL CODIGO)
        editText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // 0=left, 1=top, 2=right, 3=bottom
                val drawable = editText.compoundDrawables[drawableEnd]
                drawable?.let {
                    if (event.rawX >= (editText.right - it.bounds.width() - editText.paddingEnd)) {
                        // 🎯 Hicieron click en el ícono
                        Toast.makeText(context, "¡Click en el ícono!", Toast.LENGTH_SHORT).show()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
         */


    }
}