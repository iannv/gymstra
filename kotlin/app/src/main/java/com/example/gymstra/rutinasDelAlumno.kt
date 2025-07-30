package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.adapters.rutinasAdapter
import com.example.gymstra.models.RutinaModel
import com.example.gymstra.services.RutinaService
import com.example.gymstra.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class rutinasDelAlumno : AppCompatActivity() {

    lateinit var listaRutinas: List<RutinaModel>
    lateinit var recyclerRutinas: RecyclerView
    lateinit var rutinasAdapter: rutinasAdapter
    lateinit var nadaParaMostrar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rutinas_del_alumno)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val volver = findViewById<ImageView>(R.id.volverRutina)
        val tvCantidadRutinas = findViewById<TextView>(R.id.tvCantidadRutinas)
        val tvCantRutinas = findViewById<TextView>(R.id.tvCantRutinas)
        val buscador = findViewById<SearchView>(R.id.buscador)
        nadaParaMostrar = findViewById(R.id.nadaParaMostrar)
        listaRutinas = emptyList()
        recyclerRutinas = findViewById(R.id.recyclerViewRutinas)

        buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                rutinaFiltrada(newText)
                return true
            }
        })

        volver.setOnClickListener {
            val intent = Intent(this, inicio::class.java)
            startActivity(intent)
        }

        // Recycler View Adapter
        val recyclerViewRutinas = findViewById<RecyclerView>(R.id.recyclerViewRutinas)

        // Servicios
        val rutinaService = ServiceBuilder.buildService(RutinaService::class.java)
        val call = rutinaService.getRutinas()

        call.enqueue(object : retrofit2.Callback<List<RutinaModel>> {
            override fun onResponse(
                call: Call<List<RutinaModel>>,
                response: Response<List<RutinaModel>>
            ) {
                if (response.isSuccessful){
                    val rutinas = response.body()!!

                    if (rutinas.isEmpty()){
                        recyclerViewRutinas.visibility = View.GONE
                        tvCantidadRutinas.visibility = View.GONE
                        tvCantRutinas.visibility = View.GONE
                        nadaParaMostrar.visibility = View.VISIBLE
                        nadaParaMostrar.text = "Todavía no hay rutinas registradas"
                    }
                    else {
                        recyclerViewRutinas.visibility = View.VISIBLE
                        tvCantidadRutinas.visibility = View.VISIBLE
                        tvCantRutinas.visibility = View.VISIBLE
                        nadaParaMostrar.visibility = View.GONE

                        listaRutinas = rutinas

                        recyclerViewRutinas.apply {
                            layoutManager = LinearLayoutManager(this@rutinasDelAlumno)
                            rutinasAdapter = rutinasAdapter(rutinas)
                            recyclerRutinas.adapter = rutinasAdapter
                        }
                        tvCantidadRutinas.text = rutinas.size.toString()
                    }
                }

                else {
                    Toast.makeText(this@rutinasDelAlumno, "No se pudieron mostrar las rutinas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RutinaModel>>, t: Throwable) {
                Toast.makeText(this@rutinasDelAlumno, "Error al mostrar las rutinas", Toast.LENGTH_SHORT).show()
                Log.e("RETROFIT , Error: ", t.message.toString())
            }

        })


        // Si se toma un ID del alumno, se debe mostra el nombre del alumno, sino que se oculte


    }


    private fun rutinaFiltrada(text: String?) {
        var nuevaListaFiltrada = mutableListOf<RutinaModel>()
        for (rutina in listaRutinas) {
            if (rutina.nombre.lowercase().contains(text.toString().lowercase())) {
                nuevaListaFiltrada.add(rutina)
            }
        }
        if (nuevaListaFiltrada.isEmpty()){
            recyclerRutinas.visibility = View.GONE
            nadaParaMostrar.visibility = View.VISIBLE
            nadaParaMostrar.text = "No se encontró ninguna rutina"
        }
        else {
            nadaParaMostrar.visibility = View.GONE
            recyclerRutinas.visibility = View.VISIBLE
            rutinasAdapter.setListaFiltrada(nuevaListaFiltrada)
        }
    }

}