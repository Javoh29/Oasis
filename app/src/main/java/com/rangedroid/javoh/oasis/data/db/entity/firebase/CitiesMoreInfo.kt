package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.TypeConverters

@Keep
@Entity(tableName = "cities_more_info")
class CitiesMoreInfo() {
    @TypeConverters(DataConverterBanks::class)
    var banks: List<BanksModel> = ArrayList()

    @TypeConverters(DataConverterSights::class)
    var history_places: List<SightsModel> = ArrayList()

    @TypeConverters(DataConverterHotels::class)
    var hotels: List<HotelsModel> = ArrayList()

    @TypeConverters(DataConverterMarkets::class)
    var markets: List<MarketsModel> = ArrayList()

    @TypeConverters(DataConverterMuseums::class)
    var museums: List<MuseumsModel> = ArrayList()

    @TypeConverters(DataConverterString::class)
    var photos: List<String> = ArrayList()

    @TypeConverters(DataConverterString::class)
    var photos_night: List<String> = ArrayList()

    @TypeConverters(DataConverterRes::class)
    var restaurants: List<RestaurantsModel> = ArrayList()

    var text_info_en: String = ""

    var text_info_ru: String = ""

    constructor(
        banks: List<BanksModel>,
        history_places: List<SightsModel>,
        hotels: List<HotelsModel>,
        markets: List<MarketsModel>,
        museums: List<MuseumsModel>,
        photos: List<String>,
        photos_night: List<String>,
        restaurants: List<RestaurantsModel>,
        text_info_en: String,
        text_info_ru: String
    ) : this(){
        this.banks = banks
        this.history_places = history_places
        this.hotels = hotels
        this.markets = markets
        this.museums = museums
        this.photos = photos
        this.photos_night = photos_night
        this.restaurants = restaurants
        this.text_info_en = text_info_en
        this.text_info_ru = text_info_ru
    }

    override fun toString(): String {
        return "CitiesMoreInfo(banks=$banks, history_places=$history_places, hotels=$hotels, markets=$markets, museums=$museums, photos=$photos, photos_night=$photos_night, restaurants=$restaurants, text_info_en='$text_info_en', text_info_ru='$text_info_ru')"
    }

}