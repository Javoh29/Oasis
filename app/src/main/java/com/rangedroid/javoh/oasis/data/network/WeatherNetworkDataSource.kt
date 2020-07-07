package com.rangedroid.javoh.oasis.data.network

import androidx.lifecycle.LiveData
import com.rangedroid.javoh.oasis.data.network.response.CurrentWeatherResponse
import com.rangedroid.javoh.oasis.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        id: String
    )

    suspend fun fetchFutureWeather(
        lat: String,
        lon: String
    )
}