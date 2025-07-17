package com.example.gymstra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.adapters.alumnosAdapter
import com.example.gymstra.models.AlumnoModel
import com.example.gymstra.services.AlumnoService
import com.example.gymstra.services.ServiceBuilder
import retrofit2.Response


class alumnos : AppCompatActivity() {
    // Servicio
    val alumnoService = ServiceBuilder.buildService(AlumnoService::class.java)

    // Adapter
    lateinit var recyclerAlumnos: RecyclerView
    lateinit var alumnosAdapter: alumnosAdapter
    lateinit var sinRegistros: TextView

    lateinit var listaAlumnos: List<AlumnoModel>

    //lateinit var filtro: ImageView
    lateinit var buscador: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alumnos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerAlumnos = findViewById(R.id.recyclerAlumnos)
        sinRegistros = findViewById(R.id.nadaParaMostrarAlumnos)
        buscador = findViewById(R.id.buscador)
        val btnNuevoAlumo = findViewById<Button>(R.id.btnNuevoAlumno)
        val volver = findViewById<ImageView>(R.id.imgCerrarSesion2)


        buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listaFiltrada(newText)
                return true
            }

        })


        btnNuevoAlumo.setOnClickListener {
            val intent = Intent(this, nuevoAlumno::class.java)
            startActivity(intent)
        }

        volver.setOnClickListener {
            val intent = Intent(this, inicio::class.java)
            startActivity(intent)
        }

        cargarListaAlumnos()
    }


    // Cargar la lista de alumnos
    private fun cargarListaAlumnos() {
        val call = alumnoService.getAlumnos()
        //filtro = findViewById(R.id.filtro)

        call.enqueue(object : retrofit2.Callback<List<AlumnoModel>> {
            override fun onResponse(
                call: retrofit2.Call<List<AlumnoModel>>,
                response: Response<List<AlumnoModel>>
            ) {
                if (response.isSuccessful) {
                    listaAlumnos = response.body()?: emptyList()
                    recyclerAlumnos.apply {
                        if (listaAlumnos.isEmpty()) {
                            sinRegistros.visibility = View.VISIBLE
                            recyclerAlumnos.visibility = View.GONE
                        }
                        else{
                            layoutManager = LinearLayoutManager(this@alumnos)
                            alumnosAdapter = alumnosAdapter(listaAlumnos) { idAlumno, nombreAlumno ->
                                confirmarEliminarAlumno(idAlumno, nombreAlumno)
                            }
                            recyclerAlumnos.adapter = alumnosAdapter
                        }

//                        filtro.setOnClickListener {
//                            Toast.makeText(this@alumnos, "Filtro", Toast.LENGTH_SHORT).show()
//                        }
                    }
                }
                else {
                    Toast.makeText(this@alumnos, "Error al mostrar la lista de alumnos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<AlumnoModel>>, t: Throwable) {
                Toast.makeText(this@alumnos, "Error al obtener los alumnos", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit", "ERROR: ${t.message}")
            }
        })
    }


    // Eliminar alumno
    private fun eliminarAlumno(idAlumno: Int){
        val alumnoService = ServiceBuilder.buildService(AlumnoService::class.java)
        val call = alumnoService.deleteAlumno(idAlumno)

        call.enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(call: retrofit2.Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful){
                    cargarListaAlumnos()
                    Toast.makeText(this@alumnos, "Alumno eliminado", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@alumnos, "Error al eliminar el alumno", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                Toast.makeText(this@alumnos, "Error", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit", "ERROR: ${t.message}")
            }

        })
    }


    // Confirmación de eliminación
    private fun confirmarEliminarAlumno(idAlumno: Int, nombreAlumno: String) {
        android.app.AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación de ${nombreAlumno.uppercase()}")
            .setPositiveButton("Aceptar") { dialog, _ ->
                eliminarAlumno(idAlumno)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    // Lista filtrada para el buscador
    private fun listaFiltrada(text: String?) {
        var nuevaListaFiltrada = mutableListOf<AlumnoModel>()
        for (alumno in listaAlumnos) {
            if (
                alumno.nombre.lowercase().contains(text.toString().lowercase()) ||
                alumno.apellido.lowercase().contains(text.toString().lowercase())
                ) {
                nuevaListaFiltrada.add(alumno)
            }
        }

        if (nuevaListaFiltrada.isEmpty()){
            recyclerAlumnos.visibility = View.GONE
            sinRegistros.visibility = View.VISIBLE
            sinRegistros.text = "No se encontró ningún alumno"
        }
        else {
            sinRegistros.visibility = View.GONE
            recyclerAlumnos.visibility = View.VISIBLE
            alumnosAdapter.setListaFiltrada(nuevaListaFiltrada)
        }
    }

}

