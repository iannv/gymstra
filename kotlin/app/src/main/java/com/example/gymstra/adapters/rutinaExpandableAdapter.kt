package com.example.gymstra.adapters

import android.content.Context
import android.media.Image
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R
import com.example.gymstra.models.EjercicioModel
import com.example.gymstra.models.RutinaModel

class rutinaExpandableAdapter(private val rutinas: MutableList<RutinaModel> ): RecyclerView.Adapter<rutinaExpandableAdapter.ViewHolder>(){

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
        // Item_rutina_ejercicio
        holder.tvAgregarEjercicio.setOnClickListener {
            val inflater = LayoutInflater.from(holder.itemView.context)
            val nuevaVista = inflater.inflate(R.layout.item_rutina_ejercicio, holder.contenedorEjercicios, false)

            val spinnerEjercicios = nuevaVista.findViewById<Spinner>(R.id.spinnerEjercicios)
            val etSeries = nuevaVista.findViewById<EditText>(R.id.etSeries)
            val etReps = nuevaVista.findViewById<EditText>(R.id.etReps)

            spinnerEjercicios.setSelection(0)
            etReps.setText(rutina.repeticiones)
            etSeries.setText(rutina.series)

            rutina.ejercicio = rutina.ejercicio ?: mutableListOf()
            spinnerEjercicios.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (!rutina.ejercicio.contains(position)) {
                        rutina.ejercicio.add(position)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(holder.itemView.context, "Ningún ejercicio seleccionado", Toast.LENGTH_SHORT).show()
                }

            })

            etReps.doAfterTextChanged { editable ->
                rutina.repeticiones = editable?.toString()?.toIntOrNull() ?: 0
            }

            etSeries.doAfterTextChanged { editable ->
                rutina.series = editable?.toString()?.toIntOrNull() ?: 0
            }

            holder.contenedorEjercicios.addView(nuevaVista)
        }



    }


    override fun getItemCount(): Int {
        return rutinas.size
    }

}