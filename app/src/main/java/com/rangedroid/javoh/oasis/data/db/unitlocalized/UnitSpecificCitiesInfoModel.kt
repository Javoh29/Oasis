package com.rangedroid.javoh.oasis.data.db.unitlocalized

import androidx.annotation.Keep
import androidx.room.ColumnInfo

@Keep
data class UnitSpecificCitiesInfoModel(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "area")
    var area: String,
    @ColumnInfo(name = "climate")
    var climate: String,
    @ColumnInfo(name = "height")
    var height: String,
    @ColumnInfo(name = "pop")
    var pop: String,
    @ColumnInfo(name = "tel_code")
    var tel_code: String,
    @ColumnInfo(name = "likes")
    var likes: Int,
    @ColumnInfo(name = "dataSnap")
    var dataSnap: String,
    @ColumnInfo(name = "cityPhoto")
    var cityPhoto: String,
    @ColumnInfo(name = "cityPhotoParm")
    var cityPhotoParm: String,
    @ColumnInfo(name = "cityPhotoNight")
    var cityPhotoNight: String,
    @ColumnInfo(name = "cityPhotoParmNight")
    var cityPhotoParmNight: String
)