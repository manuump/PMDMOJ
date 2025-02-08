package com.example.ociojaen.data.models

class Comentario(
    var usuario: String,
    var comentario: String
) {
    override fun toString(): String {
        return "Comentario(usuario='$usuario', comentario='$comentario')"
    }
}
