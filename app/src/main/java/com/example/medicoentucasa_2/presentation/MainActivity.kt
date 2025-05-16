package com.example.medicoentucasa_2.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicoentucasa_2.R
import com.example.medicoentucasa_2.databinding.ActivityMainBinding
import com.example.medicoentucasa_2.presentation.remote.ApiService
import com.example.medicoentucasa_2.presentation.remote.RetrofitClient
import com.example.medicoentucasa_2.presentation.remote.SmartwatchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: HealthMetricsAdapter
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 2000L // 2 segundos
    private var metricsList: List<HealthMetric> = emptyList() // NUEVO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el ActionBar con estilo personalizado
        supportActionBar?.apply {
            title = getString(R.string.app_header_title)
            setDisplayShowTitleEnabled(true)
        }

        setupRecyclerView()
        setupSendButton() // NUEVO
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

    private fun setupSendButton() {
        binding.btnSendData.setOnClickListener {
            if (metricsList.isNotEmpty()) {
                val data = SmartwatchData(
                    smartFrecCardiaca = getCleanNumber("Frecuencia Cardíaca"), // 72
                    smartPresSistolica = getCleanNumber("Presión Sistólica"), // 120
                    smartPresDiasistolica = getCleanNumber("Presión Diastólica"),
                    smartPresO2 = getCleanNumber("SpO₂"),
                    smartTemperatura = getCleanDouble("Temperatura"),
                    smartPasos = getCleanNumber("Pasos"),
                    smartCaloQuem = getCleanNumber("Calorías Quemadas"),
                    smartSleepHoras = getCleanDouble("Horas de Sueño"),
                    smartNivelEstres = getCleanString("Nivel de Estrés"), // "Moderado"
                    smartActividadFisica = getCleanString("Actividad Física")
                )

                RetrofitClient.api.sendMetrics(data).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Toast.makeText(this@MainActivity, "Datos enviados con éxito", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error al enviar datos", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    // Nuevos métodos helper
    private fun getCleanNumber(title: String): Int? {
        val value = metricsList.find { it.title == title }?.value
        return value?.replace(Regex("[^0-9]"), "")?.toIntOrNull()
    }

    private fun getCleanDouble(title: String): Double? {
        val value = metricsList.find { it.title == title }?.value
        return value?.replace(Regex("[^0-9.]"), "")?.toDoubleOrNull()
    }

    private fun getCleanString(title: String): String? {
        return metricsList.find { it.title == title }?.value
            ?.replace(Regex("[^a-zA-ZáéíóúÁÉÍÓÚ ]"), "")
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
                metricsList = metrics // NUEVO
                adapter.updateMetrics(metrics)
                handler.postDelayed(this, updateInterval)
            }
        })
    }

    // Métodos de simulación de datos
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