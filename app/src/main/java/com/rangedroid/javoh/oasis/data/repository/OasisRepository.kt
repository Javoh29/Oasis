package com.rangedroid.javoh.oasis.data.repository

import androidx.lifecycle.LiveData
import com.rangedroid.javoh.oasis.data.db.entity.current.Climate
import com.rangedroid.javoh.oasis.data.db.entity.current.Weather
import com.rangedroid.javoh.oasis.data.db.entity.current.Wind
import com.rangedroid.javoh.oasis.data.db.entity.firebase.CitiesMoreInfo
import com.rangedroid.javoh.oasis.data.db.entity.firebase.ToursCategoryModelEn
import com.rangedroid.javoh.oasis.data.db.entity.firebase.ToursCategoryModelRu
import com.rangedroid.javoh.oasis.data.db.unitlocalized.UnitSpecificCitiesInfoModel

interface OasisRepository {
    suspend fun getCurrentClimate(): LiveData<List<Climate>>
    suspend fun getCurrentWeather(): LiveData<List<Weather>>
    suspend fun getCurrentWind(): LiveData<List<Wind>>
    suspend fun getCitiesInfo(): LiveData<out List<UnitSpecificCitiesInfoModel>>
    suspend fun getCitiesMoreInfo(): LiveData<List<CitiesMoreInfo>>
    suspend fun getToursEn():LiveData<out List<ToursCategoryModelEn>>
    suspend fun getToursRu():LiveData<out List<ToursCategoryModelRu>>
}