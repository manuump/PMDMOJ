package com.example.ociojaen.adapter

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ociojaen.R
import com.example.ociojaen.data.models.Evento
import java.io.File

class EventoAdapter(
    private var eventos: List<Evento>,
    private val onEliminarClick: (Evento) -> Unit,
    private val onEditarClick: (Evento) -> Unit
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

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

        // Cargar la imagen correctamente (desde archivo o desde drawable)
        if (evento.imagen.isNotEmpty()) {
            val imageFile = File(evento.imagen)
            if (imageFile.exists()) {
                // Si la imagen es un archivo en almacenamiento, la cargamos con BitmapFactory
                val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                holder.ivEvento.setImageBitmap(bitmap)
            } else {
                // Si la imagen es un nombre de recurso (ej: "jaenplaza"), la cargamos como drawable
                val resId = holder.ivEvento.context.resources.getIdentifier(
                    evento.imagen, "drawable", holder.ivEvento.context.packageName
                )
                if (resId != 0) {
                    holder.ivEvento.setImageResource(resId)
                } else {
                    holder.ivEvento.setImageResource(R.drawable.defecto) // Imagen por defecto si falla
                }
            }
        } else {
            holder.ivEvento.setImageResource(R.drawable.defecto) // Imagen por defecto si no hay imagen
        }

        // Manejar clic en botón eliminar
        holder.btnEliminar.setOnClickListener {
            onEliminarClick(evento)
        }

        // Manejar clic en botón editar
        holder.btnEditar.setOnClickListener {
            onEditarClick(evento)
        }
    }

    override fun getItemCount(): Int = eventos.size

    fun actualizarLista(nuevaLista: List<Evento>) {
        eventos = nuevaLista
        notifyDataSetChanged()
    }
}
