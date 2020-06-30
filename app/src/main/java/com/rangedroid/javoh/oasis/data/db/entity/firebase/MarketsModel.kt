package com.rangedroid.javoh.oasis.data.db.entity.firebase

class MarketsModel(){

    var address: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0
    var like: Int = 0;
    var name_en: String = ""
    var name_ru: String = ""
    var timeOpen: String = ""
    var timeClose: String = ""
    var photo: String = ""
    var distance: Double = 0.0
    var dataSnap: String = ""

    constructor(
    address: String,
    lat: Double,
    lon: Double,
    like: Int,
    name_en: String,
    name_ru: String,
    timeOpen: String,
    timeClose: String,
    photo: String,
    distance: Double,
    dataSnap: String
    ) : this() {
        this.address = address
        this.lat = lat
        this.lon = lon
        this.like = like
        this.name_en = name_en
        this.name_ru = name_ru
        this.timeOpen = timeOpen
        this.timeClose = timeClose
        this.photo = photo
        this.distance = distance
        this.dataSnap = dataSnap
    }

    override fun toString(): String {
        return "MarketsModel(address='$address', lat=$lat, lon=$lon, like=$like, name_en='$name_en', name_ru='$name_ru', timeOpen='$timeOpen', timeClose='$timeClose', photo='$photo', distance=$distance, dataSnap='$dataSnap')"
    }


}