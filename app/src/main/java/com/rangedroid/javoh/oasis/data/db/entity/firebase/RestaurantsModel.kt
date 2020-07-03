package com.rangedroid.javoh.oasis.data.db.entity.firebase

class RestaurantsModel(){

    var address: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0
    var like: Int = 0
    var name: String = ""
    var tel: String = ""
    var time: String = ""
    var timeOpen: String = ""
    var timeClose: String = ""
    var prices: Int = 0
    var wifi: String = ""
    var link: String = ""
    var dataSnap: String = ""

    constructor(
    address: String,
    lat: Double,
    lon: Double,
    like: Int,
    name: String,
    tel: String,
    time: String,
    timeOpen: String,
    timeClose: String,
    prices: Int,
    wifi: String,
    link: String,
    dataSnap: String
    ) : this(){
        this.address = address
        this.lat = lat
        this.lon = lon
        this.like = like
        this.name = name
        this.tel = tel
        this.time = time
        this.timeOpen = timeOpen
        this.timeClose = timeClose
        this.prices = prices
        this.wifi = wifi
        this.link = link
        this.dataSnap = dataSnap
    }

    override fun toString(): String {
        return "RestaurantsModel(address='$address', lat=$lat, lon=$lon, like=$like, name='$name', tel='$tel', time='$time', timeOpen='$timeOpen', timeClose='$timeClose', prices=$prices, wifi='$wifi', link='$link', dataSnap='$dataSnap')"
    }

}