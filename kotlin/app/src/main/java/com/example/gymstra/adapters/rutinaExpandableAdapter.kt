package com.example.gymstra.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.gymstra.R
import com.example.gymstra.models.EjercicioModel
import com.example.gymstra.models.RutinaEjercicioModel
import com.example.gymstra.models.RutinaModel

class rutinaExpandableAdapter(
    private val rutinas: MutableList<RutinaModel>,
    private val listaEjercicios: List<EjercicioModel> // todos los ejercicios posibles
) : RecyclerView.Adapter<RutinaExpandableAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expandirLista: ImageView = view.findViewById(R.id.expandirLista)
        val contenedorEjercicios: LinearLayout = view.findViewById(R.id.contenedorEjercicios)
        val tvAgregarEjercicio: TextView = view.findViewById(R.id.tvAgregarEjercicio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rutina, parent, false))

    override fun getItemCount() = rutinas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rutina = rutinas[position]

        // Expandible
        holder.expandirLista.setOnClickListener {
            holder.contenedorEjercicios.visibility =
                if (holder.contenedorEjercicios.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        // Limpiar antes de inflar ejercicios
        holder.contenedorEjercicios.removeAllViews()

        // Inflar ejercicios existentes
        rutina.ejercicios.forEach { ejercicio ->
            agregarEjercicioView(holder, rutina, ejercicio)
        }

        // Agregar nuevo ejercicio dinámicamente
        holder.tvAgregarEjercicio.setOnClickListener {
            val nuevoEjercicio = RutinaEjercicioModel(
                id_rutina_ejercicio = null,
                ejercicio = null,
                series = 0,
                repeticiones = mutableListOf()
            )
            rutina.ejercicios.add(nuevoEjercicio)
            agregarEjercicioView(holder, rutina, nuevoEjercicio)
        }
    }

    private fun agregarEjercicioView(
        holder: ViewHolder,
        rutina: RutinaModel,
        ejercicio: RutinaEjercicioModel
    ) {
        val inflater = LayoutInflater.from(holder.itemView.context)
        val itemView = inflater.inflate(R.layout.item_rutina_ejercicio, holder.contenedorEjercicios, false)

        val etSeries: EditText = itemView.findViewById(R.id.etSeries)
        val etReps: EditText = itemView.findViewById(R.id.etReps)
        val spinnerEjercicios: Spinner = itemView.findViewById(R.id.spinnerEjercicios)

        // Setear valores iniciales
        etSeries.setText(if (ejercicio.series > 0) ejercicio.series.toString() else "")
        etReps.setText(ejercicio.repeticiones.joinToString("/"))

        // Guardar cambios dinámicamente
        etSeries.doAfterTextChanged { text ->
            ejercicio.series = text.toString().toIntOrNull() ?: 0
        }
        etReps.doAfterTextChanged { text ->
            ejercicio.repeticiones = text.toString().split("/").mapNotNull { it.toIntOrNull() }.toMutableList()
        }

        // Spinner con ejercicios disponibles
        val nombresEjercicios = listaEjercicios.map { it.nombre }
        val adapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, nombresEjercicios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEjercicios.adapter = adapter

        // Selección inicial
        ejercicio.ejercicio?.let { ex ->
            val index = listaEjercicios.indexOfFirst { it.id_ejercicio == ex.id_ejercicio }
            if (index >= 0) spinnerEjercicios.setSelection(index)
        }

        spinnerEjercicios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                ejercicio.ejercicio = listaEjercicios.getOrNull(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Agregar a contenedor
        holder.contenedorEjercicios.addView(itemView)
    }
}
