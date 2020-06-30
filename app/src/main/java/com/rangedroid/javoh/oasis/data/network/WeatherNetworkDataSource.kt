package com.rangedroid.javoh.oasis.data.network

import androidx.lifecycle.LiveData
import com.rangedroid.javoh.oasis.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        id: String
    )
}