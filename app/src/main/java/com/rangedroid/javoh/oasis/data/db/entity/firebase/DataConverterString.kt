package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverterString {
    @TypeConverter
    fun fromStringList(text: List<String?>?): String? {
        if (text == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(text, type)
    }

    @TypeConverter
    fun toStringList(text: String?): List<String?>? {
        if (text == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(text, type)
    }
}