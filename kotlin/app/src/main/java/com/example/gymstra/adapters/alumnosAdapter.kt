package com.example.gymstra.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R
import com.example.gymstra.alumnos
import com.example.gymstra.models.AlumnoModel

class alumnosAdapter(private val alumnos: List<AlumnoModel>) : RecyclerView.Adapter<alumnosAdapter.ViewHolder>(){

    val imgVerRutina = arrayOf("Ver rutina")

    val imgEliminar = arrayOf(R.drawable.eliminar)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvItemAlumno = view.findViewById<TextView>(R.id.tvItemAlumno)
        var tvItemVerRutina = view.findViewById<TextView>(R.id.tvItemVerRutina)
        var eliminarAlumno = view.findViewById<ImageView>(R.id.eliminarAlumno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val alumno = LayoutInflater.from(parent.context).inflate(R.layout.item_alumno, parent, false)
        return ViewHolder(alumno)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alumno = alumnos[position]
        holder.tvItemAlumno.text = "${alumno.nombre} ${alumno.apellido}"
        holder.tvItemVerRutina.text = imgVerRutina[0]
        holder.eliminarAlumno.setImageResource(imgEliminar[0])
    }

    override fun getItemCount(): Int {
        return alumnos.size
    }
}

