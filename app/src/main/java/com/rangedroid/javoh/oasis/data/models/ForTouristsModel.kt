package com.rangedroid.javoh.oasis.data.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForTouristsModel(
    @SerializedName("title_en")
    var titleEn: String,
    @SerializedName("title_ru")
    var titleRu: String,
    @SerializedName("text_en")
    var textEn: String,
    @SerializedName("text_ru")
    var textRu: String
) {
    override fun toString(): String {
        return "ForTouristsModel(title_en='$titleEn', title_ru='$titleRu', text_en='$textEn', text_ru='$textRu')"
    }
}