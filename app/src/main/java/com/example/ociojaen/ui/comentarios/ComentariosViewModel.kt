package com.example.ociojaen.ui.comentarios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ociojaen.data.models.Comentario
import com.example.ociojaen.data.repository.ComentarioRepository

class ComentariosViewModel : ViewModel() {

    private val repository = ComentarioRepository()
    private val _comentarios = MutableLiveData<List<Comentario>>(repository.obtenerComentarios())
    val comentarios: LiveData<List<Comentario>> = _comentarios

}
