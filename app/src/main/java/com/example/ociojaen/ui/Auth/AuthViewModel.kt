package com.example.ociojaen.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ociojaen.data.models.Usuario
import com.example.ociojaen.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    fun login(username: String, password: String) = liveData(Dispatchers.IO) {
        val response = repository.login(Usuario(username, password)).execute()
        emit(response.body())
    }

    fun register(username: String, password: String) = liveData(Dispatchers.IO) {
        val response = repository.register(Usuario(username, password)).execute()
        emit(response.body())
    }
}
