package com.example.mausam.models.ForecastModels

import com.example.mausam.models.ForecastModels.Forecastday
import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastday: List<Forecastday>
)