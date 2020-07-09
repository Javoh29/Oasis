package com.rangedroid.javoh.oasis.data.network.response

import androidx.annotation.Keep
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Keep
@Root(name = "CBU_Curr", strict = false)
data class CurrencyResponse constructor(
    @field:ElementList(
        name = "CcyNtry",
        inline = true
    )
    var ccyNtry: List<CurrencyModel>,
    @field:Attribute(name = "name")
    var name: String
)