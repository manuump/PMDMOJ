package com.example.ociojaen.data.repository

import com.example.ociojaen.data.models.Evento

class EventoRepository {

    private val listaEventos = mutableListOf(
        Evento("Castillo de Santa Catalina", "Visita guiada al Castillo", "castillosantacatalina"),
        Evento("Real Jaén CF", "Información sobre partidos", "realjaen"),
        Evento("Festival LaMonaFest", "II edición del festival", "lamonafest"),
        Evento("Jaén Plaza", "Centro comercial más grande de Jaén", "jaenplaza"),
        Evento("Expoliva", "Feria Mundial del Aceite de Oliva", "expoliva")
    )

    fun obtenerEventos(): List<Evento> = listaEventos

    fun agregarEvento(evento: Evento) {
        listaEventos.add(evento)
    }

    fun eliminarEvento(evento: Evento) {
        listaEventos.remove(evento)
    }

    fun editarEvento(posicion: Int, eventoEditado: Evento) {
        if (posicion in listaEventos.indices) {
            listaEventos[posicion] = eventoEditado
        }
    }
}
