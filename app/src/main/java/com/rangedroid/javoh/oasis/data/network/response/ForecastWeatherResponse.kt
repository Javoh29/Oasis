package com.rangedroid.javoh.oasis.data.network.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.rangedroid.javoh.oasis.data.db.entity.future.City
import com.rangedroid.javoh.oasis.data.db.entity.future.WeatherList

@Keep
data class ForecastWeatherResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: WeatherList<Any?>
)