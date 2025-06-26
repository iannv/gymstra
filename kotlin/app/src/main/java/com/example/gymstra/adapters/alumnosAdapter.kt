package com.example.gymstra.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R

class alumnosAdapter : RecyclerView.Adapter<alumnosAdapter.ViewHolder>(){

    val alumnos = arrayOf(
        "Joaquin Ensalmo Gutierrez",
        "Ian Vázquez",
        "Felipe Peralta",
        "Patricia Andrea De los Santos",
    )

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
        holder.tvItemAlumno.text = alumnos[position]
        holder.tvItemVerRutina.text = imgVerRutina[0]
        holder.eliminarAlumno.setImageResource(imgEliminar[0])
    }

    override fun getItemCount(): Int {
        return alumnos.size
    }
}