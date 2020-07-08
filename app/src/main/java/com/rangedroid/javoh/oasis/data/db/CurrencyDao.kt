package com.rangedroid.javoh.oasis.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCurrency(model: CurrencyModel)

    @Query("SELECT * from currency_model")
    fun getCurrency(): LiveData<List<CurrencyModel>>

    @Query("DELETE FROM currency_model")
    fun deleteCurrency()
}