package com.example.gymstra.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R

class ejerciciosAdapter : RecyclerView.Adapter<ejerciciosAdapter.ViewHolder>() {

    val ejercicios = arrayOf(
        "Apertura inclinada con mancuernas",
        "Press horizontal con barra",
        "Press banca vertical con mancuernas",
        "Pectoralera"
    )
    val imgEditarEjercicio = arrayOf(R.drawable.editar_verde)
    val eliminarEjercicio = arrayOf(R.drawable.eliminar )


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvItemEjercicio = view.findViewById<TextView>(R.id.tvItemEjercicio)
        var imgEditarEjercicio = view.findViewById<ImageView>(R.id.imgEditarEjercicio)
        var eliminarEjercicio = view.findViewById<ImageView>(R.id.eliminarEjercicio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rutina = LayoutInflater.from(parent.context).inflate(R.layout.item_ejercicio, parent, false)
        return ViewHolder(rutina)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItemEjercicio.text = ejercicios[position]
        holder.imgEditarEjercicio.setImageResource(imgEditarEjercicio[0])
        holder.eliminarEjercicio.setImageResource(eliminarEjercicio[0])
    }

    override fun getItemCount(): Int {
        return ejercicios.size
    }

}