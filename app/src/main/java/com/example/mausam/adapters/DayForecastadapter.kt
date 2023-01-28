package com.example.mausam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mausam.Forecastday
import com.example.mausam.databinding.ForecastRowLayoutBinding

class DayForecastadapter:RecyclerView.Adapter<DayForecastadapter.MyViewHolder>(){
    var forecast= emptyList<Forecastday>()

    class MyViewHolder(private val binding: ForecastRowLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(forecaster: Forecastday){
            binding.forecastday=forecaster
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=ForecastRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentforecast =forecast[position]
        holder.bind(currentforecast)

    }

    override fun getItemCount(): Int {
        return forecast.size

    }
    /*
    fun setData(newData: Forecast){
        val forecastDiffUtil =
            ForecastDiffUtil(forecast, newData.)
        val diffUtilResult = DiffUtil.calculateDiff(forecastDiffUtil)
        forecast = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }*/

}

