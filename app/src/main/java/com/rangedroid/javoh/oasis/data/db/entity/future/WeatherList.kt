package com.rangedroid.javoh.oasis.data.db.entity.future

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherList(
    @SerializedName("clouds")
    var clouds: Clouds,
    @SerializedName("dt")
    var dt: Int,
    @SerializedName("dt_txt")
    var dtTxt: String,
    @SerializedName("main")
    var main: Main,
    @SerializedName("rain")
    var rain: Rain,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("weather")
    var weather: List<Weather>,
    @SerializedName("wind")
    var wind: Wind
)