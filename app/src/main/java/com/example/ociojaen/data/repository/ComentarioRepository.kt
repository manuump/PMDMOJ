package com.example.ociojaen.data.repository

import com.example.ociojaen.data.models.Comentario

class ComentarioRepository {

    private val listaComentarios = mutableListOf(
        Comentario("Juan Pérez", "¡Excelente evento! Muy interesante."),
        Comentario("Ana Gómez", "Me encantó el festival, lo recomiendo mucho."),
        Comentario("Carlos Ruiz", "La visita al Castillo fue espectacular, no me lo esperaba tan bien."),
        Comentario("María López", "Un centro comercial muy completo, con muchas tiendas y buenos precios.")
    )

    // Método para obtener los comentarios
    fun obtenerComentarios(): List<Comentario> {
        return listaComentarios
    }
}
