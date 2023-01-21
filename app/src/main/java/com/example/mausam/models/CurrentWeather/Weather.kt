package com.example.mausam.models.CurrentWeather


import com.example.mausam.models.CurrentWeather.Current
import com.example.mausam.models.CurrentWeather.Location
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current")
    val current: Current,
    @SerializedName("location")
    val location: Location
)
/*hese are Kotlin data classes representing weather data. The Weather class has two properties: "current" and "location", each of which is an object of the Current and Location classes
respectively. The Current class has properties related to the current weather conditions such as temperature, humidity, wind speed, and condition (code, icon, and text).
The AirQuality class has properties related to the air quality such as CO, NO2, O3, PM10, PM2.5, SO2 and Indexes. The Location class has properties related to the location
such as name, country, region, time zone, latitude and longitude. The @SerializedName annotations indicate that these properties correspond to fields in a JSON object with the same name.*/