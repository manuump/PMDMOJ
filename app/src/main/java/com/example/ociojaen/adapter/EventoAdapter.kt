package com.example.ociojaen.adapter

import android.graphics.Bitmap
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
import android.graphics.BitmapFactory
import android.util.Base64

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

        if (!evento.imagen.isNullOrEmpty()) {
            // Verificar si la imagen es Base64
            if (evento.imagen.startsWith("https")) {
                // Si es una URL, cargarla normalmente con Glide
                Glide.with(holder.itemView.context)
                    .load(evento.imagen) // Suponiendo que esto sea una URL válida
                    .error(R.drawable.error) // Imagen de error
                    .into(holder.ivImagen)
            } else {
                val bitmap = decodeBase64ToBitmap(evento.imagen)
                holder.ivImagen.setImageBitmap(bitmap)

            }
        } else {
            holder.ivImagen.setImageResource(R.drawable.error) // Imagen de error si no hay imagen
        }

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

    private fun decodeBase64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str.substring(base64Str.indexOf(",") + 1), Base64.DEFAULT) // Eliminar el prefijo de tipo de imagen
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}