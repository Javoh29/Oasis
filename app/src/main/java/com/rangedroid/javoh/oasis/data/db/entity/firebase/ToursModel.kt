package com.rangedroid.javoh.oasis.data.db.entity.firebase

class ToursModel(){

    var title: String = ""
    var days: String = ""
    var price: String = ""
    var cities: String = ""
    var season: String = ""
    var text: String = ""
    var photo: String = ""
    var url: String = ""

    constructor(
        title: String,
        days: String,
        price: String,
        cities: String,
        season: String,
        text: String,
        photo: String,
        url: String
    ) : this() {
        this.title = title
        this.days = days
        this.price = price
        this.cities = cities
        this.season = season
        this.text = text
        this.photo = photo
        this.url = url
    }

    override fun toString(): String {
        return "ToursModel(title='$title', days='$days', price='$price', cities='$cities', season='$season', text='$text', photo='$photo', url='$url')"
    }
}