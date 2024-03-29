package com.rangedroid.javoh.oasis.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rangedroid.javoh.oasis.data.db.entity.firebase.*
import com.rangedroid.javoh.oasis.data.db.unitlocalized.UnitSpecificCitiesInfoModel

@Dao
interface FirebaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCitiesInfoRu(citiesInfoModels: CitiesInfoModelsRu)

    @Query("SELECT * from firebase_cities_info_ru")
    fun getCitiesInfoRu(): LiveData<List<UnitSpecificCitiesInfoModel>>

    @Query("DELETE FROM firebase_cities_info_ru")
    fun deleteCitiesInfoRu()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCitiesInfoEn(citiesInfoModels: CitiesInfoModelsEn)

    @Query("SELECT * from firebase_cities_info_en")
    fun getCitiesInfoEn(): LiveData<List<UnitSpecificCitiesInfoModel>>

    @Query("DELETE FROM firebase_cities_info_en")
    fun deleteCitiesInfoEn()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertMoreApps(moreAppsModel: MoreAppsModel)

    @Query("SELECT * from firebase_more_apps")
    fun getMoreApps(): LiveData<List<MoreAppsModel>>

    @Query("DELETE FROM firebase_more_apps")
    fun deleteMoreApps()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCitiesMoreInfo(citiesMoreInfo: CitiesMoreInfo)

    @Query("SELECT * from cities_more_info")
    fun getCitiesMoreInfo(): LiveData<List<CitiesMoreInfo>>

    @Query("DELETE FROM cities_more_info")
    fun deleteCitiesMoreInfo()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertToursEn(toursCategoryModelEn: ToursCategoryModelEn)

    @Query("SELECT * from tours_category_en")
    fun getToursEn(): LiveData<List<ToursCategoryModelEn>>

    @Query("DELETE FROM tours_category_en")
    fun deleteToursEn()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertToursRu(toursCategoryModelRu: ToursCategoryModelRu)

    @Query("SELECT * from tours_category_ru")
    fun getToursRu(): LiveData<List<ToursCategoryModelRu>>

    @Query("DELETE FROM tours_category_ru")
    fun deleteToursRu()
}