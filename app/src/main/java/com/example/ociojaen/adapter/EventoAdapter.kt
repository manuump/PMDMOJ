package com.example.ociojaen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ociojaen.R
import com.example.ociojaen.data.models.Evento

class EventoAdapter(
    private var eventos: List<Evento>, // Lista de eventos
    private val onEliminarClick: (Evento) -> Unit, // Callback para eliminar
    private val onEditarClick: (Evento) -> Unit
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    // ViewHolder para mantener las referencias de cada item
    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivEvento: ImageView = itemView.findViewById(R.id.ivEvento)
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]

        // Configurar datos del evento
        holder.tvTitulo.text = evento.titulo
        holder.tvDescripcion.text = evento.descripcion

        // Configura la imagen
        val resId = holder.ivEvento.context.resources.getIdentifier(evento.imagen, "drawable", holder.ivEvento.context.packageName)
        holder.ivEvento.setImageResource(resId)

        // Manejar clic en botón eliminar
        holder.btnEliminar.setOnClickListener {
            onEliminarClick(evento) // Callback para eliminar el evento
        }

        // Manejar clic en botón editar
        holder.btnEditar.setOnClickListener {
            // Al hacer clic, solo pasamos el evento editado
            val eventoEditado = Evento("Evento Editado", "Descripción Editada", "imagen_editada")
            onEditarClick(eventoEditado) // Callback para editar el evento
        }
    }


    override fun getItemCount(): Int = eventos.size

    // Método para actualizar la lista de eventos
    fun actualizarLista(nuevaLista: List<Evento>) {
        eventos = nuevaLista
        notifyDataSetChanged()
    }
}

