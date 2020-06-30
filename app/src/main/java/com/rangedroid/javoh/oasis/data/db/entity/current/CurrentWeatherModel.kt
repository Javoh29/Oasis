package com.rangedroid.javoh.oasis.data.db.entity.current

class CurrentWeatherModel(){
    var climate: List<Climate> = ArrayList()
    var weather: List<Weather> = ArrayList()
    var wind: List<Wind> = ArrayList()

    constructor(climate: List<Climate>, weather: List<Weather>, wind: List<Wind>) : this(){
        this.climate = climate
        this.weather = weather
        this.wind = wind
    }
}