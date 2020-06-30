package com.rangedroid.javoh.oasis.data.db.entity.current


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "current_climate")
data class Climate(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("humidity")
    val humidity: Double, // 22
    @SerializedName("temp")
    val temp: Double // 37
)