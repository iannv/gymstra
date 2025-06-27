package com.example.gymstra.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R

class rutinasAdapter : RecyclerView.Adapter<rutinasAdapter.ViewHolder>() {

    val rutinas = arrayOf(
        "Full body",
        "ABS",
        "Funcional",
        "Tren superior"
    )

    val gradientCards = arrayOf(
        R.drawable.gradient_azul_celeste
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombreRutina = itemView.findViewById<TextView>(R.id.tvRutinaAlumno)
        var gradientCard = itemView.findViewById<CardView>(R.id.cardItemRutina)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rutina = LayoutInflater.from(parent.context).inflate(R.layout.item_rutina_alumno, parent, false)
        return ViewHolder(rutina)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombreRutina.text = rutinas[position]
        holder.gradientCard.setCardBackgroundColor(gradientCards[0])
    }

    override fun getItemCount(): Int {
        return rutinas.size
    }
}