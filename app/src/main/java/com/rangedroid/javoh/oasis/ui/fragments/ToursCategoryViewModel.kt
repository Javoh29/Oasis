package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModel
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.data.repository.OasisRepository
import com.rangedroid.javoh.oasis.utils.lazyDeferred

class ToursCategoryViewModel(
    private val oasisRepository: OasisRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    fun toursModelEn() = lazyDeferred{
        oasisRepository.getToursEn()
    }

    fun toursModelRu() = lazyDeferred{
        oasisRepository.getToursRu()
    }

    val mUnitProvider = unitProvider
}