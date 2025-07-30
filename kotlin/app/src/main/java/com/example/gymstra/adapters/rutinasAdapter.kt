package com.example.gymstra.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R
import com.example.gymstra.models.RutinaModel
import com.example.gymstra.rutinasDelAlumnoDetalle

class rutinasAdapter(private var rutinas : List<RutinaModel>) : RecyclerView.Adapter<rutinasAdapter.ViewHolder>() {

    val gradientCards = arrayOf(
        R.drawable.gradient_azul_celeste,
        R.drawable.gradient_rosa_verde,
        R.drawable.gradient_naranja_coral,
        R.drawable.gradient_naranja_verde,
        R.drawable.gradient_rosa_rojo,
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
        val rutina = rutinas[position]
        holder.nombreRutina.text = rutina.nombre

        val context = holder.itemView.context
        val fondo = ContextCompat.getDrawable(context, gradientCards[position % gradientCards.size])
        holder.gradientCard.background = fondo

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Rutina seleccionada: ${rutina.nombre}", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, rutinasDelAlumnoDetalle::class.java)
            intent.putExtra("nombreRutina", rutina.nombre)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return rutinas.size
    }


    fun setListaFiltrada(rutina: List<RutinaModel>) {
        this.rutinas = rutina
        notifyDataSetChanged()
    }
}