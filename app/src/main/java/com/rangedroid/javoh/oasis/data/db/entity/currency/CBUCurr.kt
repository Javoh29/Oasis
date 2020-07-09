package com.rangedroid.javoh.oasis.data.db.entity.currency

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

data class CBUCurr(
    @ElementList(name = "CcyNtry")
    var ccyNtry: List<CurrencyModel>,
    @Element(name = "name")
    var name: String
)