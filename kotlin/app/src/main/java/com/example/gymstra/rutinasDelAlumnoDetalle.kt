package com.example.gymstra

import android.app.Service
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.gymstra.adapters.rutinaExpandableAdapter
import com.example.gymstra.models.RutinaModel
import com.example.gymstra.services.RutinaService
import com.example.gymstra.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class rutinasDelAlumnoDetalle : AppCompatActivity() {
    lateinit var rutinaService: RutinaService
    lateinit var recyclerRutinas: RecyclerView

    lateinit var tvNombreAlumnoA: TextView
    lateinit var tvNombreRutinaA: TextView
    lateinit var tvFechaRutinaA: TextView
    lateinit var imgPdf: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rutinas_del_alumno_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerRutinas = findViewById(R.id.recyclerViewRutinasAlumno)
        tvNombreAlumnoA = findViewById(R.id.tvNombreAlumnoA)
        tvNombreRutinaA = findViewById(R.id.tvNombreRutinaA)
        tvFechaRutinaA = findViewById(R.id.tvFechaRutinaA)
        imgPdf = findViewById(R.id.imgPdf)

        obtenerRutinas()

    }


    // Obtener todas las rutinas
    private fun obtenerRutinas(){
        rutinaService = ServiceBuilder.buildService(RutinaService::class.java)
        val call = rutinaService.getRutinas()

        call.enqueue(object : retrofit2.Callback<List<RutinaModel>> {
            override fun onResponse(
                call: Call<List<RutinaModel>>,
                response: Response<List<RutinaModel>>
            ) {
                if (response.isSuccessful){
                    val rutinas = response.body() ?: emptyList()
                    recyclerRutinas.apply{
                        layoutManager = LinearLayoutManager(this@rutinasDelAlumnoDetalle)
                        adapter = rutinaExpandableAdapter(rutinas)
                        // TODO:
                    }
                }
                else{
                    Toast.makeText(this@rutinasDelAlumnoDetalle, "Error al obtener las rutinas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RutinaModel>>, t: Throwable) {
                Toast.makeText(this@rutinasDelAlumnoDetalle, "Error al obtener el listado de rutinas", Toast.LENGTH_SHORT).show()
                Log.e("Error", t.message.toString())
            }
        })
    }

}