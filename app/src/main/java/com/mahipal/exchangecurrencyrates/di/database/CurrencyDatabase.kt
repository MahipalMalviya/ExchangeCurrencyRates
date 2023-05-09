package com.mahipal.exchangecurrencyrates.di.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahipal.exchangecurrencyrates.model.CurrencyData
import com.mahipal.exchangecurrencyrates.model.CurrencyRateEntity
import com.mahipal.exchangecurrencyrates.model.MapConverter

@Database(entities = [CurrencyRateEntity::class], version = 1)
@TypeConverters(value = [MapConverter::class])
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyRateDao(): CurrencyRateDao

    companion object {
        private const val DATABASE_NAME = "currencyRates.db"

        @Volatile
        private var instance: CurrencyDatabase? = null

        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                CurrencyDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context)
        }
    }

}