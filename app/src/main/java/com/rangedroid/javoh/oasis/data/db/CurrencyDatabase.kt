package com.rangedroid.javoh.oasis.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel


@Database(
    entities = [CurrencyModel::class],
    version = 1
)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object{
        @Volatile private var instance: CurrencyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CurrencyDatabase::class.java, "currency.db")
                .build()
    }
}