package com.example.medicoentucasa_2.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicoentucasa_2.R

class HealthMetricsAdapter : RecyclerView.Adapter<HealthMetricsAdapter.MetricViewHolder>() {

    private val metrics = mutableListOf<HealthMetric>()

    fun updateMetrics(newMetrics: List<HealthMetric>) {
        metrics.clear()
        metrics.addAll(newMetrics)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetricViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_metric, parent, false)
        return MetricViewHolder(view)
    }

    override fun onBindViewHolder(holder: MetricViewHolder, position: Int) {
        holder.bind(metrics[position])
    }

    override fun getItemCount(): Int = metrics.size

    class MetricViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvMetricTitle)
        private val tvValue: TextView = itemView.findViewById(R.id.tvMetricValue)

        fun bind(metric: HealthMetric) {
            tvTitle.text = metric.title
            tvValue.text = metric.value
        }
    }
}