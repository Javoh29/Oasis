package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Keep
@Entity(tableName = "cities_more_info")
class CitiesMoreInfo() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @TypeConverters(DataConverterBanks::class)
    var banks: List<BanksModel> = ArrayList()

    @TypeConverters(DataConverterSights::class)
    var sights: List<SightsModel> = ArrayList()

    @TypeConverters(DataConverterHotels::class)
    var hotels: List<HotelsModel> = ArrayList()

    @TypeConverters(DataConverterMarkets::class)
    var markets: List<MarketsModel> = ArrayList()

    @TypeConverters(DataConverterMuseums::class)
    var museums: List<MuseumsModel> = ArrayList()

    @TypeConverters(DataConverterString::class)
    var photos: List<String> = ArrayList()

    @TypeConverters(DataConverterString::class)
    var photosNight: List<String> = ArrayList()

    @TypeConverters(DataConverterRes::class)
    var restaurants: List<RestaurantsModel> = ArrayList()

    var textInfoEn: String = ""

    var textInfoRu: String = ""

    var dataSnap: String = ""

    constructor(
        banks: List<BanksModel>,
        sights: List<SightsModel>,
        hotels: List<HotelsModel>,
        markets: List<MarketsModel>,
        museums: List<MuseumsModel>,
        photos: List<String>,
        photosNight: List<String>,
        restaurants: List<RestaurantsModel>,
        textInfoEn: String,
        textInfoRu: String,
        dataSnap: String
    ) : this(){
        this.banks = banks
        this.sights = sights
        this.hotels = hotels
        this.markets = markets
        this.museums = museums
        this.photos = photos
        this.photosNight = photosNight
        this.restaurants = restaurants
        this.textInfoEn = textInfoEn
        this.textInfoRu = textInfoRu
        this.dataSnap = dataSnap
    }

    override fun toString(): String {
        return "CitiesMoreInfo(id=$id, banks=$banks, sights=$sights, hotels=$hotels, markets=$markets, museums=$museums, photos=$photos, photosNight=$photosNight, restaurants=$restaurants, textInfoEn='$textInfoEn', textInfoRu='$textInfoRu', dataSnap='$dataSnap')"
    }


}