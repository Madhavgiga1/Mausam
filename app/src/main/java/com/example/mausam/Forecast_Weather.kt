package com.example.mausam


import com.google.gson.annotations.SerializedName

data class Forecast_Weather(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)