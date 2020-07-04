package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Keep
@Entity(tableName = "tours_category_en")
class ToursCategoryModelEn() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title: String = ""
    var photo: String = ""
    @TypeConverters(DataConverterTours::class)
    var tours: List<ToursModel> = ArrayList()
    var dataSnap: String = ""

    constructor(title: String, photo: String, tours: List<ToursModel>, dataSnap: String) : this() {
        this.title = title
        this.photo = photo
        this.tours = tours
        this.dataSnap = dataSnap
    }

    override fun toString(): String {
        return "ToursCategoryModel(title='$title', photo='$photo', tours=$tours, dataSnap='$dataSnap')"
    }

}