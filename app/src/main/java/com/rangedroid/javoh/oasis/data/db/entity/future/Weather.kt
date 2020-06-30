package com.rangedroid.javoh.oasis.data.db.entity.future


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Weather(
    @SerializedName("main")
    val main: String // Clear
)