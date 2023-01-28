package com.example.mausam.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mausam.Forecast_Weather
import com.example.mausam.data.Repository

import com.example.mausam.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: Repository, application: Application) : AndroidViewModel(application) {

    var forecastResponse:MutableLiveData<NetworkResult<Forecast_Weather>> = MutableLiveData()

    fun getForecast(queries:Map<String,String>)=viewModelScope.launch{
        getForecastSafeCall(queries)
    }

    private suspend fun getForecastSafeCall(queries: Map<String, String>) {
        forecastResponse.value=NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response=repository.remote.getForecast(queries)//the response received from retrofit
                forecastResponse.value=handleForecastResponse(response)
            }catch (e:Exception){
                forecastResponse.value = NetworkResult.Error("Weather error not found.")
            }
        }
        else{
            forecastResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleForecastResponse(response:Response<Forecast_Weather>): NetworkResult<Forecast_Weather> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                if(foodRecipes != null) {
                    return NetworkResult.Success(foodRecipes)
                } else {
                    return NetworkResult.Error("No data found.")
                }
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}
