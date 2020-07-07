package com.rangedroid.javoh.oasis.data.db.entity.future


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    var deg: Int,
    @SerializedName("speed")
    var speed: Double
)