package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "firebase_cities_info_en")
class CitiesInfoModelsEn(){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var area: String = ""
    var climate: String = ""
    var height: String = ""
    var pop: String = ""
    var tel_code: String = ""
    var likes: Int = 0
    var dataSnap: String = ""
    var cityPhoto: String = ""
    var cityPhotoParm: String = ""
    var cityPhotoNight: String = ""
    var cityPhotoParmNight: String = ""

    constructor(
        name: String,
        area: String,
        climate: String,
        height: String,
        pop: String,
        tel_code: String,
        likes: Int,
        dataSnap: String,
        cityPhoto: String,
        cityPhotoParm: String,
        cityPhotoNight: String,
        cityPhotoParmNight: String
    ) : this(){
        this.name = name
        this.area = area
        this.climate = climate
        this.height = height
        this.pop = pop
        this.tel_code = tel_code
        this.likes = likes
        this.dataSnap = dataSnap
        this.cityPhoto = cityPhoto
        this.cityPhotoParm = cityPhotoParm
        this.cityPhotoNight = cityPhotoNight
        this.cityPhotoParmNight = cityPhotoParmNight
    }

    override fun toString(): String {
        return "CitiesInfoModels(id=$id, name='$name', area='$area', climate='$climate', height='$height', pop='$pop', tel_code='$tel_code', likes=$likes, dataSnap='$dataSnap', cityPhoto='$cityPhoto', cityPhotoParm='$cityPhotoParm', cityPhotoNight='$cityPhotoNight', cityPhotoParmNight='$cityPhotoParmNight')"
    }
}