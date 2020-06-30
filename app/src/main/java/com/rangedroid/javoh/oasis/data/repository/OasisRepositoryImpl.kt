package com.rangedroid.javoh.oasis.data.repository

import android.text.Html
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.rangedroid.javoh.oasis.OasisApplication.Companion.isStartActivity
import com.rangedroid.javoh.oasis.data.db.CurrentWeatherDao
import com.rangedroid.javoh.oasis.data.db.FirebaseDao
import com.rangedroid.javoh.oasis.data.db.entity.current.Climate
import com.rangedroid.javoh.oasis.data.db.entity.current.Weather
import com.rangedroid.javoh.oasis.data.db.entity.current.Wind
import com.rangedroid.javoh.oasis.data.db.entity.firebase.CitiesInfoModelsEn
import com.rangedroid.javoh.oasis.data.db.entity.firebase.CitiesInfoModelsRu
import com.rangedroid.javoh.oasis.data.db.entity.firebase.MoreAppsModel
import com.rangedroid.javoh.oasis.data.db.unitlocalized.UnitSpecificCitiesInfoModel
import com.rangedroid.javoh.oasis.data.network.WeatherNetworkDataSource
import com.rangedroid.javoh.oasis.data.network.response.CurrentWeatherResponse
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.utils.UnitLoadFirebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OasisRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val firebaseDao: FirebaseDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val unitProvider: UnitProvider
) : OasisRepository {

    private val cities = "cities"
    private val cityInfoEn = "city_info_en"
    private val cityInfoRu = "city_info_ru"
    private val moreApps = "more_apps"
    private val appsRu = "apps_ru"
    private val appsEn = "apps_en"
    private val cityMoreInfo = "city_more_info_en"
    private val cityPhotoInfo = "photos_night"
    private val cityPhoto = "photos"
    private val cityTextEn = "text_info_en"
    private val cityTextRu = "text_info_ru"
    private val myRef: DatabaseReference = FirebaseDatabase.getInstance().reference

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
        }
    }

    override suspend fun getCurrentClimate(): LiveData<List<Climate>> {
        if (unitProvider.isOnline() && isStartActivity) {
            fetchCurrentWeather()
        }
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getClimate()
        }
    }

    override suspend fun getCurrentWeather(): LiveData<List<Weather>> {
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getWeather()
        }
    }

    override suspend fun getCurrentWind(): LiveData<List<Wind>> {
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getWind()
        }
    }

    private suspend fun onStartLoadBase() {
        withContext(Dispatchers.Default) {
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(dbError: DatabaseError) {
                    Log.e("BAG", dbError.message)
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child("update_base").value.toString() == "yes") {
                        firebaseDao.deleteCitiesInfoEn()
                        firebaseDao.deleteCitiesInfoEn()
                        loadFirebase(dataSnapshot)
                    } else {
                        if (unitProvider.getUnitLoadFirebase() == UnitLoadFirebase.NOT) {
                            loadFirebase(dataSnapshot)
                        }
                    }
                }

            })
        }
    }

    private fun loadFirebase(dataSnapshot: DataSnapshot){
        //Load CitiesInfo dataRu
        for (ds in dataSnapshot.child(cities).child(cityInfoRu).children) {
            val citiesInfo = CitiesInfoModelsRu(
                name = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.name,
                area = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.area,
                climate = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.climate,
                height = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.height,
                likes = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.likes,
                pop = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(ds.key.toString())
                    .getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.pop,
                tel_code = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.tel_code,
                cityPhoto = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.cityPhoto,
                cityPhotoParm = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.cityPhotoParm,
                cityPhotoNight = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.cityPhotoNight,
                cityPhotoParmNight = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.cityPhotoParmNight,
                dataSnap = ds.key.toString()
            )
            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertCitiesInfoRu(citiesInfo)
            }
        }

        //Load CitiesInfo dataEn
        for (ds in dataSnapshot.child(cities).child(cityInfoEn).children) {
            val citiesInfo = CitiesInfoModelsEn(
                name = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.name,
                area = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.area,
                climate = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.climate,
                height = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.height,
                likes = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.likes,
                pop = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString())
                    .getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.pop,
                tel_code = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.tel_code,
                cityPhoto = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.cityPhoto,
                cityPhotoParm = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.cityPhotoParm,
                cityPhotoNight = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.cityPhotoNight,
                cityPhotoParmNight = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(ds.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.cityPhotoParmNight,
                dataSnap = ds.key.toString()
            )
            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertCitiesInfoEn(citiesInfo)
            }

        }

        //Load MoreApps
        for (ds in dataSnapshot.child(moreApps).child(appsRu).children) {
            val model = MoreAppsModel(
                app_info_ru = dataSnapshot.child(moreApps).child(appsRu)
                    .child(ds.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_info_ru,
                app_info_en = dataSnapshot.child(moreApps).child(appsEn)
                    .child(ds.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_info_en,
                photo_app = dataSnapshot.child(moreApps).child(appsEn)
                    .child(ds.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.photo_app,
                app_name_ru = dataSnapshot.child(moreApps).child(appsRu)
                    .child(ds.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_name_ru,
                app_name_en = dataSnapshot.child(moreApps).child(appsRu)
                    .child(ds.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_name_en,
                app_url = dataSnapshot.child(moreApps).child(appsRu)
                    .child(ds.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_url,
                btn_text_en = dataSnapshot.child(moreApps).child(appsRu)
                    .child(ds.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.btn_text_en,
                btn_text_ru = dataSnapshot.child(moreApps).child(appsRu)
                    .child(ds.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.btn_text_ru
            )
            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertMoreApps(model)
            }
        }

        for (ds in dataSnapshot.child(cities).child(cityMoreInfo).children){

        }
        unitProvider.setUnitLoadFirebase(true)
    }

    override suspend fun getCitiesInfo(): LiveData<List<UnitSpecificCitiesInfoModel>> {
        if (unitProvider.isOnline()) {
            onStartLoadBase()
        }
        return withContext(Dispatchers.IO) {
            return@withContext if (unitProvider.isLocale()) firebaseDao.getCitiesInfoEn()
            else firebaseDao.getCitiesInfoRu()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsertClimate(fetchedWeather.climate)
            currentWeatherDao.upsertWeather(fetchedWeather.weather[0])
            currentWeatherDao.upsertWind(fetchedWeather.wind)
        }
    }

    private suspend fun fetchCurrentWeather() {
        currentWeatherDao.deleteClimate()
        currentWeatherDao.deleteWeather()
        currentWeatherDao.deleteWind()
        val idList: List<String> = arrayListOf(
            "1512569",
            "1216265",
            "1217662",
            "1512473",
            "1514019",
            "1484846",
            "1513157",
            "601294",
            "1513886",
            "1513966",
            "1513131",
            "1216311",
            "1114926"
        )
        idList.forEach {
            weatherNetworkDataSource.fetchCurrentWeather(it)
        }
        isStartActivity = false
    }

}