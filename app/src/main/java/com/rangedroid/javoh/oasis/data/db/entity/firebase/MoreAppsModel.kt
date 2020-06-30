package com.rangedroid.javoh.oasis.data.db.entity.firebase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "firebase_more_apps")
class MoreAppsModel(){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var app_info_en: String = ""
    var app_info_ru: String = ""
    var photo_app: String = ""
    var app_name_en: String = ""
    var app_name_ru: String = ""
    var app_url: String = ""
    var btn_text_en: String = ""
    var btn_text_ru: String = ""

    constructor(
        app_info_en: String,
        app_info_ru: String,
        photo_app: String,
        app_name_en: String,
        app_name_ru: String,
        app_url: String,
        btn_text_en: String,
        btn_text_ru: String
    ) : this(){
        this.app_info_en = app_info_en
        this.app_info_ru = app_info_ru
        this.photo_app = photo_app
        this.app_name_en = app_name_en
        this.app_name_ru = app_name_ru
        this.app_url = app_url
        this.btn_text_en = btn_text_en
        this.btn_text_ru = btn_text_ru
    }

    override fun toString(): String {
        return "MoreAppsModel(id=$id, app_info_en='$app_info_en', app_info_ru='$app_info_ru', photo_app='$photo_app', app_name_en='$app_name_en', app_name_ru='$app_name_ru', app_url='$app_url', btn_text_en='$btn_text_en', btn_text_ru='$btn_text_ru')"
    }


}