package com.rangedroid.javoh.oasis.data.db.entity.currency

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Keep
@Entity(tableName = "currency_model")
@Root(name = "CcyNtry")
data class CurrencyModel @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @field:Element(name = "Ccy",required=false)
    var ccy: String = "",
    @field:Element(name = "CcyMnrUnts",required=false)
    var ccyMnrUnts: String = "",
    @field:Element(name = "CcyNm_EN",required=false)
    var ccyNmEN: String = "",
    @field:Element(name = "CcyNm_RU",required=false)
    var ccyNmRU: String = "",
    @field:Element(name = "CcyNm_UZ",required=false)
    var ccyNmUZ: String = "",
    @field:Element(name = "CcyNm_UZC",required=false)
    var ccyNmUZC: String = "",
    @field:Element(name = "date",required=false)
    var date: String = "",
    @field:Element(name = "Nominal",required=false)
    var nominal: String = "",
    @field:Element(name = "Rate",required=false)
    var rate: String = "",
    @field:Attribute(name = "ID")
    var ccyId: String = ""
)