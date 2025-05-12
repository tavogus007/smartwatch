package com.example.medicoentucasa_2.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.medicoentucasa_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: HealthMetricsAdapter
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 2000L // 2 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        startDataUpdates()
    }

    private fun setupRecyclerView() {
        adapter = HealthMetricsAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            setEdgeItemsCenteringEnabled(true)
            isVerticalScrollBarEnabled = true
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    private fun startDataUpdates() {
        handler.post(object : Runnable {
            override fun run() {
                val metrics = listOf(
                    HealthMetric("Frecuencia Cardíaca", "${getHeartRate()} lpm"),
                    HealthMetric("Presión Sistólica", "${getBloodPressureSistolic()} mmHg"),
                    HealthMetric("Presión Diastólica", "${getBloodPressureDiastolic()} mmHg"),
                    HealthMetric("SpO₂", "${getBloodO2()}%"),
                    HealthMetric("Temperatura", "%.1f°C".format(getBodyTemp())),
                    HealthMetric("Pasos", getSteps().toString()),
                    HealthMetric("Calorías Quemadas", "${getCaloriesBurned()} kcal"),
                    HealthMetric("Horas de Sueño", "%.1f hrs".format(getSleepHours())),
                    HealthMetric("Nivel de Estrés", getStressLevel()),
                    HealthMetric("Actividad Física", getPhysicalActivity())
                )
                adapter.updateMetrics(metrics)
                handler.postDelayed(this, updateInterval)
            }
        })
    }

    // Métodos de simulación de datos actualizados para coincidir con tu tabla
    private fun getHeartRate(): Int = (60..100).random()
    private fun getBloodPressureSistolic(): Int = (110..140).random()
    private fun getBloodPressureDiastolic(): Int = (70..90).random()
    private fun getBloodO2(): Int = (90..100).random()
    private fun getBodyTemp(): Double = 36.0 + (0..20).random() / 10.0
    private fun getSteps(): Int = (0..10000).random()
    private fun getCaloriesBurned(): Int = (100..800).random()
    private fun getSleepHours(): Double = "%.1f".format(4.0 + Math.random() * 5.0).toDouble()
    private fun getStressLevel(): String = listOf("Bajo", "Moderado", "Alto").random()
    private fun getPhysicalActivity(): String = listOf("Sedentario", "Ligero", "Moderado", "Intenso", "Muy intenso").random()
}