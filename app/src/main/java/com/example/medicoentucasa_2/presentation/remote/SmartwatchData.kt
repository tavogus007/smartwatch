package com.example.medicoentucasa_2.presentation.remote

data class SmartwatchData(
    val heartRate: String,
    val systolicPressure: String,
    val diastolicPressure: String,
    val oxygenSaturation: String,
    val temperature: String,
    val steps: String,
    val calories: String,
    val sleepHours: String,
    val stress: String,
    val physicalActivity: String
)