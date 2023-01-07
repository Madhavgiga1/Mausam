package com.example.mausam.network

import com.example.mausam.models.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {

    @GET(value = "/current.json")
    suspend fun getWeather(
        @QueryMap queries: Map<String, String>
    ): Response<Weather>
}