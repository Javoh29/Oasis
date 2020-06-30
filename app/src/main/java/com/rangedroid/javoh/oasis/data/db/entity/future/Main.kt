package com.rangedroid.javoh.oasis.data.db.entity.future


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double, // 30.32
    @SerializedName("humidity")
    val humidity: Double, // 15
    @SerializedName("pressure")
    val pressure: Double, // 1013
    @SerializedName("temp")
    val temp: Double // 33.71
)