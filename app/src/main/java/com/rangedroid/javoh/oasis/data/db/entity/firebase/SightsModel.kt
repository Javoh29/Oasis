package com.rangedroid.javoh.oasis.data.db.entity.firebase

class SightsModel(){
    var address: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0
    var like: Int = 0
    var name_en: String = ""
    var name_ru: String = ""
    var photo: String = ""
    var link: String = ""
    var dataSnap: String = ""

    constructor(
        address: String,
        lat: Double,
        lon: Double,
        like: Int,
        name_en: String,
        name_ru: String,
        photo: String,
        link: String,
        dataSnap: String
    ) : this(){
        this.address = address
        this.lat = lat
        this.lon = lon
        this.like = like
        this.name_en = name_en
        this.name_ru = name_ru
        this.photo = photo
        this.link = link
        this.dataSnap = dataSnap
    }

    override fun toString(): String {
        return "SightsModel(address='$address', lat=$lat, lon=$lon, like=$like, name_en='$name_en', name_ru='$name_ru', photo='$photo', link='$link', dataSnap='$dataSnap')"
    }

}