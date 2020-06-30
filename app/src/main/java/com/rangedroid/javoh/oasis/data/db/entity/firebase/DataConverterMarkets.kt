package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverterMarkets {

    @TypeConverter
    fun fromModelList(model: List<MarketsModel?>?): String? {
        if (model == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MarketsModel?>?>() {}.type
        return gson.toJson(model, type)
    }

    @TypeConverter
    fun toModelList(model: String?): List<MarketsModel?>? {
        if (model == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MarketsModel?>?>() {}.type
        return gson.fromJson(model, type)
    }
}