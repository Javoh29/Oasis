package com.rangedroid.javoh.oasis.data.db.entity.current


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "current_list")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    var weatherId: Int,
    @SerializedName("main")
    val main: String // Clear
)