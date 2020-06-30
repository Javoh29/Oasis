package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.TypeConverters

@Keep
@Entity(tableName = "cities_more_info")
class CitiesMoreInfo() {
    @TypeConverters(DataConverterBanks::class)
    val banks: List<BanksModel> = ArrayList()

    @TypeConverters(DataConverterSights::class)
    val history_places: List<SightsModel> = ArrayList()

    @TypeConverters(DataConverterHotels::class)
    val hotels: List<HotelsModel> = ArrayList()

    @TypeConverters(DataConverterMarkets::class)
    val markets: List<MarketsModel> = ArrayList()

    @TypeConverters(DataConverterMuseums::class)
    val museums: List<MuseumsModel> = ArrayList()
}