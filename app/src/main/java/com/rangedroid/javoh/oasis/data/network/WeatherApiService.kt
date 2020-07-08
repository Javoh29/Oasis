package com.rangedroid.javoh.oasis.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rangedroid.javoh.oasis.data.network.response.CurrentWeatherResponse
import com.rangedroid.javoh.oasis.data.network.response.FutureWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    //https://api.openweathermap.org/data/2.5/weather?id=1512569&units=metric&appid=3fa04f32646d64af46d2bb9e83f625b0
    @GET("weather")
    fun getCurrentWeatherAsync(
        @Query("id") id: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = "3fa04f32646d64af46d2bb9e83f625b0"
    ) : Deferred<CurrentWeatherResponse>

    //https://api.openweathermap.org/data/2.5/forecast?lat=&lon=&units=metric&appid=3fa04f32646d64af46d2bb9e83f625b0
    @GET("forecast")
    fun getForecastWeatherAsync(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = "3fa04f32646d64af46d2bb9e83f625b0"
    ) : Deferred<FutureWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}