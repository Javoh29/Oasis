package com.rangedroid.javoh.oasis.data.db.entity.currency

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Keep
@Entity(tableName = "currency_model")
@Root(name = "CcyNtry", strict = false)
data class CurrencyModel constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @field:Element(name = "Ccy")
    var ccy: String,
    @field:Element(name = "CcyMnrUnts")
    var ccyMnrUnts: String,
    @field:Element(name = "CcyNm_EN")
    var ccyNmEN: String,
    @field:Element(name = "CcyNm_RU")
    var ccyNmRU: String,
    @field:Element(name = "CcyNm_UZ")
    var ccyNmUZ: String,
    @field:Element(name = "CcyNm_UZC")
    var ccyNmUZC: String,
    @field:Element(name = "date")
    var date: String,
    @field:Element(name = "Nominal")
    var nominal: String,
    @field:Element(name = "Rate")
    var rate: Double
)