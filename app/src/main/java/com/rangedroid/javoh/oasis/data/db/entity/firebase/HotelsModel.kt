package com.rangedroid.javoh.oasis.data.db.entity.firebase

class HotelsModel(){
    var address: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0
    var like: Int = 0
    var name: String = ""
    var tel: String = ""
    var time: String = ""
    var timeOut: String = ""
    var timeIn: String = ""
    var website: String = ""
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
        timeOut: String,
        timeIn: String,
        website: String,
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
        this.timeOut = timeOut
        this.timeIn = timeIn
        this.website = website
        this.wifi = wifi
        this.link = link
        this.dataSnap = dataSnap
    }

    override fun toString(): String {
        return "HotelsModel(address='$address', lat=$lat, lon=$lon, like=$like, name='$name', tel='$tel', time='$time', timeOut='$timeOut', timeIn='$timeIn', website='$website', wifi='$wifi', link='$link', dataSnap='$dataSnap')"
    }

}