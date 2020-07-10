package com.rangedroid.javoh.oasis.data.network.response

import androidx.annotation.Keep
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Keep
@Root(name = "CBU_Curr")
data class CurrencyResponse @JvmOverloads constructor(
    @field:ElementList(
        required = false,
        name = "CcyNtry",
        inline = true,
        entry = "CcyNtry",
        empty = true
    )
    var ccyNtry: List<CurrencyModel> = ArrayList(),
    @field:Attribute(name = "name")
    var name: String = ""
)