package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModel
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.data.repository.OasisRepository
import com.rangedroid.javoh.oasis.utils.lazyDeferred

class InfoViewModel(
    private val oasisRepository: OasisRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    fun citiesMoreInfo() = lazyDeferred {
        oasisRepository.getCitiesMoreInfo()
    }

    val mUnitProvider = unitProvider
}