package com.example.ociojaen.api

import com.example.ociojaen.data.models.Usuario
import com.example.ociojaen.data.models.AuthResponse
import retrofit2.Call
import com.example.ociojaen.data.models.Evento
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("usuarios/register")
    fun register(@Body usuario: Usuario): Call<AuthResponse>

    @POST("usuarios/login")
    fun login(@Body usuario: Usuario): Call<AuthResponse>

    @GET("eventos")
    suspend fun getEventos(@Header("Authorization") token: String): Response<List<Evento>>

    @GET("eventos/{id}")
    suspend fun getEventoById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Evento>

    @POST("eventos")
    suspend fun createEvento(
        @Header("Authorization") token: String,
        @Body evento: Evento
    ): Response<Evento>

    @PUT("eventos/{id}")
    suspend fun updateEvento(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body evento: Evento
    ): Response<Unit>

    @DELETE("eventos/{id}")
    suspend fun deleteEvento(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>
}
