package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModel
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.data.repository.OasisRepository
import com.rangedroid.javoh.oasis.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class HomeViewModel(
    private val oasisRepository: OasisRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    fun climate() = lazyDeferred{
        oasisRepository.getCurrentClimate()
    }

    fun weather() = lazyDeferred {
        oasisRepository.getCurrentWeather()
    }

    fun wind() = lazyDeferred {
        oasisRepository.getCurrentWind()
    }

    fun citiesInfoModels() = lazyDeferred {
        oasisRepository.getCitiesInfo()
    }

    val mUnitProvider = unitProvider

}
