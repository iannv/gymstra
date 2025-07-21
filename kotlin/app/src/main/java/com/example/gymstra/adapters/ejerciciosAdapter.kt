package com.example.gymstra.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R
import com.example.gymstra.models.EjercicioModel

class ejerciciosAdapter(private var ejercicios: List<EjercicioModel>, private val eliminarEjercicioId: (Int, String) -> Unit) : RecyclerView.Adapter<ejerciciosAdapter.ViewHolder>() {

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
        val ejercicio = ejercicios[position]
        holder.tvItemEjercicio.text = ejercicio.nombre
        holder.imgEditarEjercicio.setImageResource(imgEditarEjercicio[0])
        holder.eliminarEjercicio.setImageResource(eliminarEjercicio[0])

        holder.eliminarEjercicio.setOnClickListener {
            ejercicio.id_ejercicio?.let { id ->
                val nombreEjercicio = ejercicio.nombre
                eliminarEjercicioId(id, nombreEjercicio)
            }
        }
    }

    override fun getItemCount(): Int {
        return ejercicios.size
    }


    // Actualizar la lista filtrada del chip seleccionado
    fun actualizarListaFiltrada(nuevaLista: List<EjercicioModel>){
        ejercicios = nuevaLista
        notifyDataSetChanged()
    }
}