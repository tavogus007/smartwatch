package com.example.medicoentucasa_2.presentation.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @PUT("smartwatch/{smartId}")
    fun updateMetrics(
        @Path("smartId") smartId: Int,
        @Body data: SmartwatchData
    ): Call<Void>
}