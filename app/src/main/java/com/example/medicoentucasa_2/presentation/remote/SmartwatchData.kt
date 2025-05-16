package com.example.medicoentucasa_2.presentation.remote

data class SmartwatchData(
    val smartEstado: Int = 1,
    val smartFrecCardiaca: Int?,
    val smartPresSistolica: Int?,
    val smartPresDiasistolica: Int?,
    val smartPresO2: Int?,
    val smartTemperatura: Double?,
    val smartPasos: Int?,
    val smartCaloQuem: Int?,
    val smartSleepHoras: Double?,
    val smartNivelEstres: String?,
    val smartActividadFisica: String?
)