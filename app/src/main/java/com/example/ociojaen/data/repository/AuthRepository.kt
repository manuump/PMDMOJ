package com.example.ociojaen.data.repository

import com.example.ociojaen.api.RetrofitClient
import com.example.ociojaen.data.models.Usuario

class AuthRepository {
    private val api = RetrofitClient.apiService

    fun register(usuario: Usuario) = api.register(usuario)
    fun login(usuario: Usuario) = api.login(usuario)
}
