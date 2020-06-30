package com.rangedroid.javoh.oasis.data.db.entity.future

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherList<T>(
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: WeatherList<Weather>,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("wind")
    val wind: Wind
)