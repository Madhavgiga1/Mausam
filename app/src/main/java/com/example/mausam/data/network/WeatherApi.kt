package com.example.mausam.data.network

import com.example.mausam.Forecast_Weather
import com.example.mausam.models.CurrentWeather.Weather

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    //base url di me dependency injection use karte time bana hua h
    @GET(value = "/v1/current.json")
    suspend fun getWeather(
        @QueryMap queries: Map<String, String>
    ): Response<Weather>

    @GET(value="/v1/forecast.json")
    suspend fun getForecast(@QueryMap forecastqueries: Map<String, String>)
    :Response<Forecast_Weather>
}

/*In Retrofit, an interface is used to define the methods that can be called on a REST API. It is a contract that specifies the request methods, the relative URL paths, and the
 request and response types for each method.
 To use Retrofit to call a REST API, you need to define an interface that specifies the methods that can be called on the API and
 the parameters and return types for those methods. You can then use the Retrofit library to generate an implementation of the interface that can be used to call the methods on the API.
* */