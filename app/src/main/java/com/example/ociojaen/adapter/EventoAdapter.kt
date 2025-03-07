package com.example.ociojaen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ociojaen.R
import com.example.ociojaen.data.models.Evento

class EventoAdapter(
    private var eventos: List<Evento>,
    private val onEliminarClick: (Evento) -> Unit,
    private val onEditarClick: (Evento) -> Unit
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val ivImagen : ImageView = itemView.findViewById(R.id.ivEvento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]

        holder.tvTitulo.text = evento.titulo
        holder.tvDescripcion.text = evento.descripcion

        Glide.with(holder.itemView.context)
            .load(evento.imagen) // URL de la imagen
            .error(R.drawable.error) // Imagen de error
            .into(holder.ivImagen)

        // Botón para eliminar
        holder.btnEliminar.setOnClickListener {
            onEliminarClick(evento)
        }

        // Botón para editar
        holder.btnEditar.setOnClickListener {
            onEditarClick(evento)
        }
    }

    override fun getItemCount(): Int = eventos.size

    // Método para actualizar la lista de eventos
    fun actualizarLista(nuevaLista: List<Evento>) {
        eventos = nuevaLista
        notifyDataSetChanged()
    }
}