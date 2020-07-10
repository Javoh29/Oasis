package com.rangedroid.javoh.oasis.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rangedroid.javoh.oasis.data.network.response.CurrentWeatherResponse
import com.rangedroid.javoh.oasis.data.network.response.FutureWeatherResponse
import com.rangedroid.javoh.oasis.utils.NoConnectivityException
import java.net.UnknownHostException

class WeatherNetworkDataSourceImpl(
    private val weatherApiService: WeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchCurrentWeather(id: String) {
        try {
            val fetchedCurrentWeather = weatherApiService
                .getCurrentWeatherAsync(id)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }catch (e: UnknownHostException){
            Log.e("BAG", "unknown exception. ", e)
        }
    }

    override suspend fun fetchFutureWeather(lat: String, lon: String) {
        try {
            val fetchedFutureWeather = weatherApiService.getForecastWeatherAsync(lat = lat, lon = lon).await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        }catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }catch (e: UnknownHostException){
            Log.e("BAG", "unknown exception. ", e)
        }
    }
}