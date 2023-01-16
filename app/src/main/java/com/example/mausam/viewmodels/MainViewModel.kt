package com.example.mausam.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mausam.data.Repository
import com.example.mausam.models.Weather
import com.example.mausam.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var weatherResponse: MutableLiveData<NetworkResult<Weather>> = MutableLiveData()

    fun getWeather(queries: Map<String, String>) = viewModelScope.launch {
        getWeatherSafeCall(queries)
    }

    private suspend fun getWeatherSafeCall(queries: Map<String, String>) {
        weatherResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getWeather(queries)
                weatherResponse.value = handleWeatherResponse(response)
            }catch (e: Exception) {
                weatherResponse.value = NetworkResult.Error("Weather error not found.")
            }
        } else {
            weatherResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleWeatherResponse(response: Response<Weather>): NetworkResult<Weather> {
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