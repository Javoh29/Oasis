package com.rangedroid.javoh.oasis.data.network.response

import com.google.gson.annotations.SerializedName
import com.rangedroid.javoh.oasis.data.db.entity.current.Climate
import com.rangedroid.javoh.oasis.data.db.entity.current.Weather
import com.rangedroid.javoh.oasis.data.db.entity.current.Wind

data class CurrentWeatherResponse(
    @SerializedName("main")
    val climate: Climate,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)