package com.example.medicoentucasa_2.presentation // Asegúrate de que coincida con tu package name real

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.medicoentucasa_2.R

class MainActivity : AppCompatActivity() {
    private lateinit var tvBloodO2: TextView
    private lateinit var tvBodyTemp: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 2000L // 2 segundos
    private var isUpdating = false // Bandera para controlar las actualizaciones

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (!isUpdating) return // Si la actividad está en pausa/destruida, no actualices

            // Actualiza las vistas si están inicializadas
            if (::tvBloodO2.isInitialized && ::tvBodyTemp.isInitialized) {
                tvBloodO2.text = getString(R.string.spo2_label, getBloodO2())
                tvBodyTemp.text = getString(R.string.temp_label, getBodyTemp())
            }

            // Programa la próxima actualización solo si la actividad está activa
            if (isUpdating) {
                handler.postDelayed(this, updateInterval)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvBloodO2 = findViewById(R.id.tvBloodO2)
        tvBodyTemp = findViewById(R.id.tvBodyTemp)
        isUpdating = true
        handler.post(updateRunnable) // Inicia las actualizaciones
    }

    override fun onDestroy() {
        super.onDestroy()
        isUpdating = false // Detiene las actualizaciones
        handler.removeCallbacks(updateRunnable)
    }

    // Simulador de datos (sin cambios)
    private fun getBloodO2(): Int = (90..100).random()
    private fun getBodyTemp(): Double = 36.0 + (0..20).random() / 10.0
}