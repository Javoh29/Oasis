package com.rangedroid.javoh.oasis.data.db.entity.firebase

class SightsModel(){
    var address: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0
    var like: Int = 0
    var nameEn: String = ""
    var nameRu: String = ""
    var photo: String = ""
    var link: String = ""
    var dataSnap: String = ""

    constructor(
        address: String,
        lat: Double,
        lon: Double,
        like: Int,
        nameEn: String,
        nameRu: String,
        photo: String,
        link: String,
        dataSnap: String
    ) : this(){
        this.address = address
        this.lat = lat
        this.lon = lon
        this.like = like
        this.nameEn = nameEn
        this.nameRu = nameRu
        this.photo = photo
        this.link = link
        this.dataSnap = dataSnap
    }

    override fun toString(): String {
        return "SightsModel(address='$address', lat=$lat, lon=$lon, like=$like, nameEn='$nameEn', nameRu='$nameRu', photo='$photo', link='$link', dataSnap='$dataSnap')"
    }

}