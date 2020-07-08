package com.rangedroid.javoh.oasis.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rangedroid.javoh.oasis.data.network.response.CurrencyResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

interface CurrencyApiService {

    @GET("arkhiv-kursov-valyut/xml/")
    fun getCurrencyAsync(): Deferred<CurrencyResponse>

    @Suppress("DEPRECATION")
    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): CurrencyApiService {
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
                .baseUrl("http://cbu.uz/oz/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(CurrencyApiService::class.java)
        }
    }
}