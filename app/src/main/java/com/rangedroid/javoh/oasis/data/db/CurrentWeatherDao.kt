package com.rangedroid.javoh.oasis.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rangedroid.javoh.oasis.data.db.entity.current.Climate
import com.rangedroid.javoh.oasis.data.db.entity.current.Weather
import com.rangedroid.javoh.oasis.data.db.entity.current.Wind

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertClimate(climate: Climate)

    @Query("SELECT * from current_climate")
    fun getClimate(): LiveData<List<Climate>>

    @Query("DELETE FROM current_climate")
    fun deleteClimate()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertWeather(weather: Weather)

    @Query("SELECT * from current_list")
    fun getWeather(): LiveData<List<Weather>>

    @Query("DELETE FROM current_list")
    fun deleteWeather()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertWind(wind: Wind)

    @Query("SELECT * from current_wind")
    fun getWind(): LiveData<List<Wind>>

    @Query("DELETE FROM current_wind")
    fun deleteWind()

}