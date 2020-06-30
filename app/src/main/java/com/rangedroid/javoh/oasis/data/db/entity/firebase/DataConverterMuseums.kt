package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverterMuseums {
    @TypeConverter
    fun fromModelList(model: List<MuseumsModel?>?): String? {
        if (model == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MuseumsModel?>?>() {}.type
        return gson.toJson(model, type)
    }

    @TypeConverter
    fun toModelList(model: String?): List<MuseumsModel?>? {
        if (model == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MuseumsModel?>?>() {}.type
        return gson.fromJson(model, type)
    }
}