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
import com.example.gymstra.models.EjercicioModel
import com.example.gymstra.nuevoEjercicio
import kotlin.contracts.contract

class ejerciciosAdapter(
    private var ejercicios: List<EjercicioModel>,
    val context: Context,
    private val eliminarEjercicioId: (Int, String) -> Unit) : RecyclerView.Adapter<ejerciciosAdapter.ViewHolder>() {

    val imgEditarEjercicio = arrayOf(R.drawable.editar_verde)
    val eliminarEjercicio = arrayOf(R.drawable.eliminar)


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

        holder.imgEditarEjercicio.setOnClickListener {
            val intentEditar = Intent(context, nuevoEjercicio::class.java)
            intentEditar.putExtra("accion", "editar")
            intentEditar.putExtra("id_ejercicio", ejercicio.id_ejercicio)
            intentEditar.putExtra("nombre", ejercicio.nombre)
            intentEditar.putExtra("id_zona_muscular", ejercicio.id_zona_muscular)
            context.startActivity(intentEditar)
        }

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