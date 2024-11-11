package com.example.almacenamientosqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ParquesAdapter(private val parquesList: List<Parque>) :
    RecyclerView.Adapter<ParquesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView:
                TextView = itemView.findViewById(R.id.textIdParque)
        val nombreTextView:
                TextView = itemView.findViewById(R.id.textNombreParque)
        val ubicacionTextView:
                TextView = itemView.findViewById(R.id.textUbicacionParque)
        val tipoTextView:
                TextView = itemView.findViewById(R.id.textTipoParque)
        val tamanoTextView:
                TextView = itemView.findViewById(R.id.textTamanoParque)
        val descripcionTextView:
                TextView = itemView.findViewById(R.id.textDescripcionParque)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parque, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parque = parquesList[position]

        holder.idTextView.text = buildString {
            append("ID: ")
            append(parque.id)
        }

        holder.nombreTextView.text = buildString {
            append("Nombre: ")
            append(parque.nombre)
        }

        holder.ubicacionTextView.text = buildString {
            append("Ubicación: ")
            append(parque.ubicacion)
        }

        holder.tipoTextView.text = buildString {
            append("Tipo: ")
            append(parque.tipo)
        }

        holder.tamanoTextView.text = buildString {
            append("Tamaño: ")
            append(parque.tamano.toString())
        }

        holder.descripcionTextView.text = buildString {
            append("Descripción: ")
            append(parque.descripcion)
        }
    }

    override fun getItemCount(): Int = parquesList.size
}
