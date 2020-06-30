package com.rangedroid.javoh.oasis.data.db.entity.future


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class City(
    @SerializedName("name")
    val name: String
)