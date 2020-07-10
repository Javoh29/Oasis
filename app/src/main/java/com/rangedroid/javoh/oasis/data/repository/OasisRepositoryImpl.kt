package com.rangedroid.javoh.oasis.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.rangedroid.javoh.oasis.OasisApplication.Companion.isStartActivity
import com.rangedroid.javoh.oasis.data.db.CurrencyDao
import com.rangedroid.javoh.oasis.data.db.CurrentWeatherDao
import com.rangedroid.javoh.oasis.data.db.FirebaseDao
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel
import com.rangedroid.javoh.oasis.data.db.entity.current.Climate
import com.rangedroid.javoh.oasis.data.db.entity.current.Weather
import com.rangedroid.javoh.oasis.data.db.entity.current.Wind
import com.rangedroid.javoh.oasis.data.db.entity.firebase.*
import com.rangedroid.javoh.oasis.data.db.unitlocalized.UnitSpecificCitiesInfoModel
import com.rangedroid.javoh.oasis.data.network.CurrencyNetworkDataSource
import com.rangedroid.javoh.oasis.data.network.WeatherNetworkDataSource
import com.rangedroid.javoh.oasis.data.network.response.CurrencyResponse
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
    private val currencyDao: CurrencyDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val currencyNetworkDataSource: CurrencyNetworkDataSource,
    private val unitProvider: UnitProvider
) : OasisRepository {

    private val cities = "cities"
    private val cityInfoEn = "city_info_en"
    private val cityInfoRu = "city_info_ru"
    private val moreApps = "more_apps"
    private val appsRu = "apps_ru"
    private val appsEn = "apps_en"
    private val cityMoreInfo = "city_more_info_en"
    private val banks = "banks"
    private val hotels = "hotels"
    private val sights = "history_places"
    private val markets = "markets"
    private val museums = "museums"
    private val photos = "photos"
    private val photosNight = "photos_night"
    private val restaurants = "restaurants"
    private val toursRu = "tours_ru"
    private val toursEn = "tours_en"
    private val myRef: DatabaseReference = FirebaseDatabase.getInstance().reference

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever {
                currentWeatherDao.deleteClimate()
                currentWeatherDao.deleteWeather()
                currentWeatherDao.deleteWind()
                persistFetchedCurrentWeather(it)
            }
        }
        currencyNetworkDataSource.apply {
            downloadedCurrency.observeForever {
                currencyDao.deleteCurrency()
                persistFetchedCurrency(it)
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
                        firebaseDao.deleteCitiesInfoRu()
                        firebaseDao.deleteCitiesMoreInfo()
                        firebaseDao.deleteToursEn()
                        firebaseDao.deleteToursRu()
                        firebaseDao.deleteMoreApps()
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
        dataSnapshot.child(cities).child(cityInfoRu).children.forEach {
            val citiesInfo = CitiesInfoModelsRu(
                name = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.name,
                area = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.area,
                climate = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.climate,
                height = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.height,
                likes = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.likes,
                pop = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(it.key.toString())
                    .getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.pop,
                tel_code = dataSnapshot.child(cities).child(cityInfoRu)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.tel_code,
                cityPhoto = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.cityPhoto,
                cityPhotoParm = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.cityPhotoParm,
                cityPhotoNight = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.cityPhotoNight,
                cityPhotoParmNight = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsRu::class.java
                    )!!.cityPhotoParmNight,
                dataSnap = it.key.toString()
            )
            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertCitiesInfoRu(citiesInfo)
            }
        }

        //Load CitiesInfo dataEn
        dataSnapshot.child(cities).child(cityInfoEn).children.forEach {
            val citiesInfo = CitiesInfoModelsEn(
                name = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.name,
                area = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.area,
                climate = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.climate,
                height = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.height,
                likes = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.likes,
                pop = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString())
                    .getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.pop,
                tel_code = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.tel_code,
                cityPhoto = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.cityPhoto,
                cityPhotoParm = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.cityPhotoParm,
                cityPhotoNight = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.cityPhotoNight,
                cityPhotoParmNight = dataSnapshot.child(cities).child(cityInfoEn)
                    .child(it.key.toString()).getValue(
                        CitiesInfoModelsEn::class.java
                    )!!.cityPhotoParmNight,
                dataSnap = it.key.toString()
            )
            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertCitiesInfoEn(citiesInfo)
            }

        }

        //Load Tours dataRu
        dataSnapshot.child(toursRu).children.forEach {ds ->
            val toursList: ArrayList<ToursModel> = ArrayList()
            dataSnapshot.child(toursRu).child(ds.key.toString()).child("tours").children.forEach {
                val model = ToursModel(
                    title =  dataSnapshot.child(toursRu)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.title,
                    days = dataSnapshot.child(toursRu)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.days,
                    price = dataSnapshot.child(toursRu)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.price,
                    cities = dataSnapshot.child(toursRu)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.cities,
                    season = dataSnapshot.child(toursRu)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.season,
                    text = dataSnapshot.child(toursRu)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.text,
                    photo = dataSnapshot.child(toursRu)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.photo,
                    url = dataSnapshot.child(toursRu)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.url
                )
                toursList.add(model)
            }

            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertToursRu(
                    ToursCategoryModelRu(
                        title = dataSnapshot.child(toursRu).child(ds.key.toString()).child("title").value.toString(),
                        photo = dataSnapshot.child(toursRu).child(ds.key.toString()).child("photo").value.toString(),
                        tours = toursList,
                        dataSnap = ds.key.toString()
                    )
                )
            }
        }

        //Load Tours dataEn
        dataSnapshot.child(toursEn).children.forEach {ds ->
            val toursList: ArrayList<ToursModel> = ArrayList()
            dataSnapshot.child(toursEn).child(ds.key.toString()).child("tours").children.forEach {
                val model = ToursModel(
                    title =  dataSnapshot.child(toursEn)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.title,
                    days = dataSnapshot.child(toursEn)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.days,
                    price = dataSnapshot.child(toursEn)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.price,
                    cities = dataSnapshot.child(toursEn)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.cities,
                    season = dataSnapshot.child(toursEn)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.season,
                    text = dataSnapshot.child(toursEn)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.text,
                    photo = dataSnapshot.child(toursEn)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.photo,
                    url = dataSnapshot.child(toursEn)
                        .child(ds.key.toString())
                        .child("tours")
                        .child(it.key.toString())
                        .getValue(ToursModel::class.java)!!.url
                )
                toursList.add(model)
            }

            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertToursEn(
                    ToursCategoryModelEn(
                        title = dataSnapshot.child(toursEn).child(ds.key.toString()).child("title").value.toString(),
                        photo = dataSnapshot.child(toursEn).child(ds.key.toString()).child("photo").value.toString(),
                        tours = toursList,
                        dataSnap = ds.key.toString()
                    )
                )
            }
        }

        //Load MoreApps
        dataSnapshot.child(moreApps).child(appsRu).children.forEach {
            val model = MoreAppsModel(
                app_info_ru = dataSnapshot.child(moreApps).child(appsRu)
                    .child(it.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_info_ru,
                app_info_en = dataSnapshot.child(moreApps).child(appsEn)
                    .child(it.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_info_en,
                photo_app = dataSnapshot.child(moreApps).child(appsEn)
                    .child(it.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.photo_app,
                app_name_ru = dataSnapshot.child(moreApps).child(appsRu)
                    .child(it.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_name_ru,
                app_name_en = dataSnapshot.child(moreApps).child(appsRu)
                    .child(it.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_name_en,
                app_url = dataSnapshot.child(moreApps).child(appsRu)
                    .child(it.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.app_url,
                btn_text_en = dataSnapshot.child(moreApps).child(appsRu)
                    .child(it.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.btn_text_en,
                btn_text_ru = dataSnapshot.child(moreApps).child(appsRu)
                    .child(it.key.toString()).getValue(
                        MoreAppsModel::class.java
                    )!!.btn_text_ru
            )
            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertMoreApps(model)
            }
        }

        //Load Cities More Info
        for (ds in dataSnapshot.child(cities).child(cityMoreInfo).children){
            val listBanksModel: ArrayList<BanksModel> = ArrayList()
            val listHotelsModel: ArrayList<HotelsModel> = ArrayList()
            val listMarketsModel: ArrayList<MarketsModel> = ArrayList()
            val listSightsModel: ArrayList<SightsModel> = ArrayList()
            val listMuseumsModel: ArrayList<MuseumsModel> = ArrayList()
            val listRestaurantsModel: ArrayList<RestaurantsModel> = ArrayList()
            val listPhotos: ArrayList<String> = ArrayList()
            val listPhotosNight: ArrayList<String> = ArrayList()
            dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(banks).children.forEach {
                val model = BanksModel(
                    address = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.address,
                    lat = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.lat,
                    lon = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.lon,
                    like = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.like,
                    name = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.name,
                    tel = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.tel,
                    time = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.time,
                    timeOpen = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.timeOpen,
                    timeClose = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.timeClose,
                    website = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.website,
                    ccyEx = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(banks).child(it.key.toString())
                        .getValue(BanksModel::class.java)!!.ccyEx,
                    link = ds.key.toString(),
                    dataSnap = it.key.toString()
                )
                listBanksModel.add(model)
            }
            dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(hotels).children.forEach {
                val model = HotelsModel(
                    address = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.address,
                    lat = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.lat,
                    lon = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.lon,
                    like = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.like,
                    name = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.name,
                    tel = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.tel,
                    time = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.time,
                    timeOut = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.timeOut,
                    timeIn = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.timeIn,
                    website = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.website,
                    wifi = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(hotels).child(it.key.toString())
                        .getValue(HotelsModel::class.java)!!.wifi,
                    link = ds.key.toString(),
                    dataSnap = it.key.toString()
                )
                listHotelsModel.add(model)
            }
            dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(markets).children.forEach {
                val model = MarketsModel(
                    address = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.address,
                    lat = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.lat,
                    lon = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.lon,
                    like = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.like,
                    name_en = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.name_en,
                    name_ru = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.name_ru,
                    photo = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.photo,
                    timeClose = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.timeClose,
                    timeOpen = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(markets).child(it.key.toString())
                        .getValue(MarketsModel::class.java)!!.timeOpen,
                    link = ds.key.toString(),
                    dataSnap = it.key.toString()
                )
                listMarketsModel.add(model)
            }
            dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(sights).children.forEach {
                val model = SightsModel(
                    address = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(sights).child(it.key.toString())
                        .getValue(SightsModel::class.java)!!.address,
                    lat = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(sights).child(it.key.toString())
                        .getValue(SightsModel::class.java)!!.lat,
                    lon = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(sights).child(it.key.toString())
                        .getValue(SightsModel::class.java)!!.lon,
                    like = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(sights).child(it.key.toString())
                        .getValue(SightsModel::class.java)!!.like,
                    name_en = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(sights).child(it.key.toString())
                        .getValue(SightsModel::class.java)!!.name_en,
                    name_ru = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(sights).child(it.key.toString())
                        .getValue(SightsModel::class.java)!!.name_ru,
                    photo = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(sights).child(it.key.toString())
                        .getValue(SightsModel::class.java)!!.photo,
                    link = ds.key.toString(),
                    dataSnap = it.key.toString()
                )
                listSightsModel.add(model)
            }
            dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(museums).children.forEach {
                val model = MuseumsModel(
                    address = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.address,
                    lat = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.lat,
                    lon = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.lon,
                    like = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.like,
                    name_en = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.name_en,
                    name_ru = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.name_ru,
                    photo = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.photo,
                    price = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.price,
                    timeOpen = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.timeOpen,
                    timeClose = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(museums).child(it.key.toString())
                        .getValue(MuseumsModel::class.java)!!.timeClose,
                    link = ds.key.toString(),
                    dataSnap = it.key.toString()
                )
                listMuseumsModel.add(model)
            }
            dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(restaurants).children.forEach {
                val model = RestaurantsModel(
                    address = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.address,
                    lat = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.lat,
                    lon = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.lon,
                    like = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.like,
                    name = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.name,
                    prices = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.prices,
                    tel = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.tel,
                    time = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.time,
                    timeClose = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.timeClose,
                    timeOpen = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.timeOpen,
                    wifi = dataSnapshot.child(cities)
                        .child(cityMoreInfo).child(ds.key.toString())
                        .child(restaurants).child(it.key.toString())
                        .getValue(RestaurantsModel::class.java)!!.wifi,
                    link = ds.key.toString(),
                    dataSnap = it.key.toString()
                )
                listRestaurantsModel.add(model)
            }
            dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(photos).children.forEach {
                listPhotos.add(
                    dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(photos).child(it.key.toString()).value.toString()
                )
            }
            dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(photosNight).children.forEach {
                listPhotosNight.add(
                    dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child(photosNight).child(it.key.toString()).value.toString()
                )
            }
            GlobalScope.launch(Dispatchers.IO) {
                firebaseDao.upsertCitiesMoreInfo(
                    CitiesMoreInfo(
                        banks = listBanksModel,
                        hotels = listHotelsModel,
                        markets = listMarketsModel,
                        sights = listSightsModel,
                        museums = listMuseumsModel,
                        restaurants = listRestaurantsModel,
                        photos = listPhotos,
                        photosNight = listPhotosNight,
                        textInfoEn = dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child("text_info_en").value.toString(),
                        textInfoRu = dataSnapshot.child(cities).child(cityMoreInfo).child(ds.key.toString()).child("text_info_ru").value.toString(),
                        dataSnap = ds.key.toString()
                    )
                )
            }
        }

        unitProvider.setUnitLoadFirebase(true)
    }

    override suspend fun getCitiesInfo(): LiveData<out List<UnitSpecificCitiesInfoModel>> {
        if (unitProvider.isOnline()) {
            onStartLoadBase()
        }
        return withContext(Dispatchers.IO) {
            return@withContext if (unitProvider.isLocale()) firebaseDao.getCitiesInfoEn()
            else firebaseDao.getCitiesInfoRu()
        }
    }

    override suspend fun getCitiesMoreInfo(): LiveData<List<CitiesMoreInfo>> {
        return withContext(Dispatchers.IO) {
            return@withContext firebaseDao.getCitiesMoreInfo()
        }
    }

    override suspend fun getToursEn(): LiveData<out List<ToursCategoryModelEn>> {
        return withContext(Dispatchers.IO) {
            return@withContext firebaseDao.getToursEn()
        }
    }

    override suspend fun getToursRu(): LiveData<out List<ToursCategoryModelRu>> {
        return withContext(Dispatchers.IO) {
            return@withContext firebaseDao.getToursRu()
        }
    }

    override suspend fun getCurrency(): LiveData<List<CurrencyModel>> {
        if (unitProvider.isOnline()) {
            fetchCurrency()
        }
        return withContext(Dispatchers.IO) {
            return@withContext currencyDao.getCurrency()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsertClimate(fetchedWeather.climate)
            currentWeatherDao.upsertWeather(fetchedWeather.weather[0])
            currentWeatherDao.upsertWind(fetchedWeather.wind)
        }
    }

    private fun persistFetchedCurrency(currencyResponse: CurrencyResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currencyResponse.ccyNtry.forEach {
                currencyDao.upsertCurrency(it)
            }
        }
    }

    private suspend fun fetchCurrency() {
        currencyNetworkDataSource.fetchCurrency()
    }

    private suspend fun fetchCurrentWeather() {
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