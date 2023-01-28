package com.example.mausam.data

import com.example.mausam.Forecast_Weather
import com.example.mausam.models.CurrentWeather.Weather
import com.example.mausam.data.network.WeatherApi


import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val weatherApi: WeatherApi){

    suspend fun getWeather(queries: Map<String,String>):Response<Weather>{
        return weatherApi.getWeather(queries)
    }

    suspend fun getForecast(queries: Map<String, String>):Response<Forecast_Weather>{
        return weatherApi.getForecast(queries)
    }
}