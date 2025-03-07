package com.example.ociojaen.data.repository

import com.example.ociojaen.api.RetrofitClient
import com.example.ociojaen.data.models.Evento

class EventoRepository {

    private val api = RetrofitClient.apiService

    suspend fun getEventos(token: String): List<Evento>? {
        return try {
            val response = api.getEventos("Bearer $token")
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun createEvento(token: String, evento: Evento): Evento? {
        return try {
            val response = api.createEvento("Bearer $token", evento)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateEvento(token: String, id: Int, evento: Evento): Boolean {
        return try {
            val response = api.updateEvento("Bearer $token", id, evento)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteEvento(token: String, id: Int): Boolean {
        return try {
            val response = api.deleteEvento("Bearer $token", id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}