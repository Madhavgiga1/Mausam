package com.example.mausam.data

import com.example.mausam.models.CurrentWeather.Weather
import com.example.mausam.data.network.WeatherApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val weatherApi: WeatherApi){

    suspend fun getWeather(queries: Map<String,String>):Response<Weather>{
        return weatherApi.getWeather(queries)

    }
}