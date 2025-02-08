package com.example.ociojaen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ociojaen.R
import com.example.ociojaen.data.models.Comentario

class ComentarioAdapter : ListAdapter<Comentario, ComentarioAdapter.ComentarioViewHolder>(ComentarioDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return ComentarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val comentario = getItem(position)
        holder.tvUsuario.text = comentario.usuario
        holder.tvComentario.text = comentario.comentario
    }

    class ComentarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsuario: TextView = itemView.findViewById(R.id.tvUsuario)
        val tvComentario: TextView = itemView.findViewById(R.id.tvComentario)
    }

    class ComentarioDiffCallback : DiffUtil.ItemCallback<Comentario>() {
        override fun areItemsTheSame(oldItem: Comentario, newItem: Comentario): Boolean {
            return oldItem.usuario == newItem.usuario && oldItem.comentario == newItem.comentario
        }

        override fun areContentsTheSame(oldItem: Comentario, newItem: Comentario): Boolean {
            return oldItem == newItem
        }
    }
}
