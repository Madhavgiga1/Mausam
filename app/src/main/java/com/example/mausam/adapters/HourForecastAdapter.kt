package com.example.mausam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mausam.Hour
import com.example.mausam.databinding.HourlyForecastRowLayoutBinding

class HourForecastAdapter:RecyclerView.Adapter<HourForecastAdapter.MyViewHolder>() {
    var hourer= emptyList<Hour>()//the forecast day i passed from forecast fraagment had a list of hours,this is for displaying that
    class MyViewHolder(private val binding:HourlyForecastRowLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(result: Hour){
            binding.hourlyforecast=result
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):MyViewHolder{
                val inflater=LayoutInflater.from(parent.context)
                val binding=HourlyForecastRowLayoutBinding.inflate(inflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currenthour=hourer[position]
        holder.bind(currenthour)

    }

    override fun getItemCount(): Int {
        return hourer.size
    }
}