package com.example.gymstra.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R
import com.example.gymstra.models.EjercicioModel
import com.example.gymstra.models.RutinaModel

class rutinaExpandableAdapter(private val rutinas: List<RutinaModel> ): RecyclerView.Adapter<rutinaExpandableAdapter.ViewHolder>(){

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val cardItemEjercicio = view.findViewById<CardView>(R.id.cardItemEjercicio)
        val spinnerDia = view.findViewById<Spinner>(R.id.spinnerDia)
        val tvAgregarEjercicio = view.findViewById<TextView>(R.id.tvAgregarEjercicio)
        val expandirLista = view.findViewById<ImageView>(R.id.expandirLista)
        val tvZonasMusculares = view.findViewById<TextView>(R.id.tvZonasMusculares)
        val expandableLayoutRutina = view.findViewById<LinearLayout>(R.id.expandableLayoutRutina)
        val contenedorEjercicios = view.findViewById<LinearLayout>(R.id.contenedorEjercicios)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rutina = LayoutInflater.from(parent.context).inflate(R.layout.item_rutina, parent, false)
        return ViewHolder(rutina)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rutina = rutinas[position]

        // TODO: Agregar animacion al expandible
        holder.expandirLista.setOnClickListener {
            if (holder.contenedorEjercicios.visibility == View.VISIBLE) { holder.contenedorEjercicios.visibility = View.GONE }
            else { holder.contenedorEjercicios.visibility = View.VISIBLE }
        }

        // TODO: Hacer guardado automatico para que el recyclerview no reinicie y se pierda los cambios al hacer scroll
        holder.tvAgregarEjercicio.setOnClickListener {
            val inflater = LayoutInflater.from(holder.itemView.context)
            val nuevaVista = inflater.inflate(R.layout.item_rutina_ejercicio, holder.contenedorEjercicios, false)

            val spinner = nuevaVista.findViewById<Spinner>(R.id.spinner2)
            val etSeries = nuevaVista.findViewById<EditText>(R.id.etReps)
            val etReps = nuevaVista.findViewById<EditText>(R.id.etReps)

            etReps.setText("15 12 10 10 8")


            holder.contenedorEjercicios.addView(nuevaVista)
        }



    }


    override fun getItemCount(): Int {
        return rutinas.size
    }

}