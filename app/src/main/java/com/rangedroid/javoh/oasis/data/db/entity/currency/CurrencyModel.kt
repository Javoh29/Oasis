package com.rangedroid.javoh.oasis.data.db.entity.currency

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "CcyNtry")
@Entity(tableName = "currency_model")
data class CurrencyModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @Element(name = "ID")
    val ccyId: Int,
    @Element(name = "CcyNm_EN")
    val nameEn: String,
    @Element(name = "CcyNm_RU")
    val nameRu: String,
    @Element(name = "date")
    val date: String,
    @Element(name = "Rate")
    val rate: String,
    @Element(name = "Ccy")
    val ccy: String
)