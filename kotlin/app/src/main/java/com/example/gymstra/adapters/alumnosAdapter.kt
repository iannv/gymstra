package com.example.gymstra.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R
import com.example.gymstra.datosDelAlumno
import com.example.gymstra.models.AlumnoModel

class alumnosAdapter(
    private var alumnos: List<AlumnoModel>,
    var context: Context,
    private val eliminarAlumnoId: (Int, String) -> Unit) : RecyclerView.Adapter<alumnosAdapter.ViewHolder>() {

    val imgVerRutina = arrayOf("Ver rutina")

    val imgEliminar = arrayOf(R.drawable.eliminar)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvItemAlumno = view.findViewById<TextView>(R.id.tvItemAlumno)
        var tvItemVerRutina = view.findViewById<TextView>(R.id.tvItemVerRutina)
        var eliminarAlumno = view.findViewById<ImageView>(R.id.eliminarAlumno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val alumno =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alumno, parent, false)
        return ViewHolder(alumno)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alumno = alumnos[position]
        holder.tvItemAlumno.text = "${alumno.nombre} ${alumno.apellido}"
        holder.tvItemVerRutina.text = imgVerRutina[0]
        holder.eliminarAlumno.setImageResource(imgEliminar[0])

        holder.eliminarAlumno.setOnClickListener {
            alumno.id_alumno?.let { id ->
                val nombreCompleto = "${alumno.nombre} ${alumno.apellido}"
                eliminarAlumnoId(id, nombreCompleto)
            }
        }

        holder.tvItemAlumno.setOnClickListener {
            val intent = Intent(holder.itemView.context, datosDelAlumno::class.java)
            intent.putExtra("idAlumno", alumno.id_alumno)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return alumnos.size
    }


    // Setear lista filtrada para el buscador
    fun setListaFiltrada(alumnos: List<AlumnoModel>){
        this.alumnos = alumnos
        notifyDataSetChanged()
    }

}

