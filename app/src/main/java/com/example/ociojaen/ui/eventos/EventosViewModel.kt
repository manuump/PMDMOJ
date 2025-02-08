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

    fun editarEvento(eventoEditado: Evento) {
        // Buscar el evento original en la lista
        val eventoOriginal = _eventos.value?.find { it.titulo == eventoEditado.titulo }
        eventoOriginal?.let {
            // Obtener el índice del evento original
            val index = _eventos.value?.indexOf(it)
            if (index != null && index >= 0) {
                // Crear una nueva lista mutable a partir de la lista original
                val eventosActualizados = _eventos.value?.toMutableList()

                // Verificar que la lista no sea nula
                eventosActualizados?.let {
                    // Actualizar el evento en el índice correspondiente
                    it[index] = eventoEditado
                    // Asignar la nueva lista al LiveData
                    _eventos.value = it
                }
            }
        }
    }

}

