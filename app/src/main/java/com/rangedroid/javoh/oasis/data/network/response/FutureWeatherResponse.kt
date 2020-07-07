package com.rangedroid.javoh.oasis.data.network.response


import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rangedroid.javoh.oasis.data.db.entity.future.City
import com.rangedroid.javoh.oasis.data.db.entity.future.WeatherList

data class FutureWeatherResponse(
    @PrimaryKey(autoGenerate = true)
    var idWeather: Int,
    @SerializedName("city")
    var city: City,
    @SerializedName("cnt")
    var cnt: Int,
    @SerializedName("cod")
    var cod: String,
    @SerializedName("list")
    var list: List<WeatherList>,
    @SerializedName("message")
    var message: Int
)