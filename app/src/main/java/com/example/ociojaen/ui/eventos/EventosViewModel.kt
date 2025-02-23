package com.example.ociojaen.ui.eventos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ociojaen.data.models.Evento
import com.example.ociojaen.data.repository.EventoRepository

class EventosViewModel : ViewModel() {

    private val repository = EventoRepository()
    private val _eventos = MutableLiveData<List<Evento>>(repository.obtenerEventos())

    val eventos: LiveData<List<Evento>> = _eventos

    fun agregarEvento(evento: Evento) {
        repository.agregarEvento(evento)
        _eventos.value = repository.obtenerEventos() // Actualizar LiveData
    }

    fun eliminarEvento(evento: Evento) {
        repository.eliminarEvento(evento)
        _eventos.value = repository.obtenerEventos() // Actualizar LiveData
    }

    fun editarEvento(eventoOriginal: Evento, eventoEditado: Evento) {
        val eventosActualizados = _eventos.value?.toMutableList() ?: return

        val index = eventosActualizados.indexOf(eventoOriginal)
        if (index != -1) {
            // Si la imagen no se cambia, conservamos la anterior
            val imagenFinal = if (eventoEditado.imagen.isEmpty()) eventoOriginal.imagen else eventoEditado.imagen
            // Crear un nuevo evento con la imagen correcta
            val eventoActualizado = Evento(
                titulo = eventoEditado.titulo,
                descripcion = eventoEditado.descripcion,
                imagen = imagenFinal
            )

            // Actualizar la lista
            eventosActualizados[index] = eventoActualizado
            _eventos.value = eventosActualizados
        }
    }

}

