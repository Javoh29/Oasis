package com.rangedroid.javoh.oasis.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rangedroid.javoh.oasis.data.db.entity.firebase.CitiesInfoModelsEn
import com.rangedroid.javoh.oasis.data.db.entity.firebase.CitiesInfoModelsRu

@Database(
    entities = [CitiesInfoModelsEn::class, CitiesInfoModelsRu::class],
    version = 1
)
abstract class OasisDatabase: RoomDatabase() {
    abstract fun firebaseDao(): FirebaseDao

    companion object{
        @Volatile private var instance: OasisDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                OasisDatabase::class.java, "firebaseDatabase.db")
                .build()
    }
}
