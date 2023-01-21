package com.example.mausam.models.ForecastModels

import com.example.mausam.models.ForecastModels.Location
import com.google.gson.annotations.SerializedName

data class Forecast_Weather(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)