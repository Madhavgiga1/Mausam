package com.example.mausam.bindingadapter

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.mausam.Forecastday
import com.example.mausam.R
import com.example.mausam.ui.fragments.ForecastFragmentDirections

class ForecastRowBinding {
    companion object {

        @BindingAdapter("onWeatherClickListener")
        @JvmStatic
        fun onWeatherClickListener(forecastrowlayout: ConstraintLayout, result: Forecastday) {
            Log.d("onRecipeClickListener", "CALLED")
            forecastrowlayout.setOnClickListener {
                try {
                    val action = ForecastFragmentDirections.actionForecastFragmentToHourForecastFragment(result)
                    forecastrowlayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onRecipeClickListener", e.toString())
                }
            }
        }
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

    }
}