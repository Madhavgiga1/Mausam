package com.example.mausam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mausam.databinding.ForecastRowLayoutBinding

class Forecastadapter:RecyclerView.Adapter<Forecastadapter.MyViewHolder>(){
    class MyViewHolder(private var binding: ForecastRowLayoutBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            /*fun bind(result: Result){
                binding.result=result
                binding.executePendingBindings()
            }*/
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
        //val currentRecipe = recipes[position]
        //holder.bind(currentRecipe)*/
        TODO()
    }

    override fun getItemCount(): Int {
        //return recipes.size
        TODO()
    }
    /*fun setData(newData: FoodRecipe){
        val forecastDiffUtil =
            ForecastDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }*/

}

