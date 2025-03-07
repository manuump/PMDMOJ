package com.example.ociojaen.ui.eventos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ociojaen.data.models.Evento
import com.example.ociojaen.data.repository.EventoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventosViewModel : ViewModel() {

    private val repository = EventoRepository()

    private val _eventos = MutableLiveData<List<Evento>>()
    val eventos: LiveData<List<Evento>> get() = _eventos

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getEventos(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val eventos = repository.getEventos(token)
            _eventos.postValue(eventos ?: emptyList())
            if (eventos == null) {
                _errorMessage.postValue("Error al obtener los eventos")
            }
        }
    }

    fun createEvento(token: String, evento: Evento, onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.createEvento(token, evento)
            if (result != null) {
                getEventos(token)
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun updateEvento(token: String, id: Int, evento: Evento, onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.updateEvento(token, id, evento)
            if (success) {
                getEventos(token)
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun deleteEvento(token: String, id: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val success = repository.deleteEvento(token, id)
            if (success) {
                getEventos(token)
                onSuccess()
            } else {
                onError()
            }
        }
    }
}