package com.rangedroid.javoh.oasis.data.network.response

import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "CBU_Curr")
data class CurrencyResponse(
    @Element(name = "CcyNtry")
    var model: List<CurrencyModel>
)