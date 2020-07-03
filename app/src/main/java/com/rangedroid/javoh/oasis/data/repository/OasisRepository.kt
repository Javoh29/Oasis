package com.rangedroid.javoh.oasis.data.repository

import androidx.lifecycle.LiveData
import com.rangedroid.javoh.oasis.data.db.entity.current.Climate
import com.rangedroid.javoh.oasis.data.db.entity.current.Weather
import com.rangedroid.javoh.oasis.data.db.entity.current.Wind
import com.rangedroid.javoh.oasis.data.db.entity.firebase.CitiesMoreInfo
import com.rangedroid.javoh.oasis.data.db.unitlocalized.UnitSpecificCitiesInfoModel

interface OasisRepository {
    suspend fun getCurrentClimate(): LiveData<List<Climate>>
    suspend fun getCurrentWeather(): LiveData<List<Weather>>
    suspend fun getCurrentWind(): LiveData<List<Wind>>
    suspend fun getCitiesInfo(): LiveData<List<UnitSpecificCitiesInfoModel>>
    suspend fun getCitiesMoreInfo(): LiveData<List<CitiesMoreInfo>>
}