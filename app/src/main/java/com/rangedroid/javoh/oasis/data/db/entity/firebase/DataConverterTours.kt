package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverterTours {
    @TypeConverter
    fun fromModelList(model: List<ToursModel?>?): String? {
        if (model == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ToursModel?>?>() {}.type
        return gson.toJson(model, type)
    }

    @TypeConverter
    fun toModelList(model: String?): List<ToursModel?>? {
        if (model == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ToursModel?>?>() {}.type
        return gson.fromJson(model, type)
    }
}