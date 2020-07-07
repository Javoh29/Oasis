package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModel
import com.rangedroid.javoh.oasis.data.network.WeatherNetworkDataSource
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.utils.lazyDeferred

class WeatherViewModel(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    unitProvider: UnitProvider
) : ViewModel() {

    fun model(lat: String, lon: String) = lazyDeferred {
        weatherNetworkDataSource.apply {
            weatherNetworkDataSource.fetchFutureWeather(lat,lon)
        }
    }

    val mUnitProvider = unitProvider

}