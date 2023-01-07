package com.example.mausam.models


import com.example.mausam.models.Current
import com.example.mausam.models.Location
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current")
    val current: Current,
    @SerializedName("location")
    val location: Location
)