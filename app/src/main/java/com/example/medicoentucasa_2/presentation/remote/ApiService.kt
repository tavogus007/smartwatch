package com.example.medicoentucasa_2.presentation.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("smartwatch")
    fun sendMetrics(@Body data: SmartwatchData): Call<Void>
}
