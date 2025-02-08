package com.example.ociojaen.data.models

class Evento (
    var titulo : String,
    var descripcion : String,
    var imagen : String
) {
    override fun toString(): String {
        return "Evento(titulo='$titulo', descripcion='$descripcion', imagen='$imagen')"
    }
}