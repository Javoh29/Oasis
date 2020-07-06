package com.rangedroid.javoh.oasis.data.db.unitlocalized

import com.rangedroid.javoh.oasis.data.db.entity.firebase.ToursModel

data class UnitSpecificTours(
    var title: String,
    var photo: String,
    var tours: List<ToursModel>,
    var dataSnap: String
)