package com.rangedroid.javoh.oasis.data.db.entity.current


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "current_wind")
data class Wind(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("speed")
    val speed: Double // 3.1
)