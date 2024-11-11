package com.example.almacenamientosqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuardabosquesAdapter(private val guardabosquesList: List<Guardabosque>) :
    RecyclerView.Adapter<GuardabosquesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.textId)
        val nombreTextView: TextView = itemView.findViewById(R.id.textNombre)
        val edadTextView: TextView = itemView.findViewById(R.id.textEdad)
        val aniosServicioTextView: TextView = itemView.findViewById(R.id.textAniosServicio)
        val especialidadTextView: TextView = itemView.findViewById(R.id.textEspecialidad)
        val descripcionTextView: TextView = itemView.findViewById(R.id.textDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_guardabosque, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guardabosque = guardabosquesList[position]

        holder.idTextView.text = buildString {
            append("ID: ")
            append(guardabosque.id)
        }

        holder.nombreTextView.text = buildString {
            append("Nombre: ")
            append(guardabosque.nombre)
        }

        holder.edadTextView.text = buildString {
            append("Edad: ")
            append(guardabosque.edad)
        }

        holder.aniosServicioTextView.text = buildString {
            append("Años de servicio: ")
            append(guardabosque.aniosServicio)
        }

        holder.especialidadTextView.text = buildString {
            append("Especialidad: ")
            append(guardabosque.especialidad)
        }

        holder.descripcionTextView.text = buildString {
            append("Descripción: ")
            append(guardabosque.descripcion)
        }
    }

    override fun getItemCount(): Int = guardabosquesList.size
}
