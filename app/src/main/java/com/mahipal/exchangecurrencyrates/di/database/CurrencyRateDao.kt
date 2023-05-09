package com.mahipal.exchangecurrencyrates.di.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahipal.exchangecurrencyrates.model.CurrencyRateEntity
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CurrencyRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyRate(currencyRateEntity: CurrencyRateEntity)

    @Query("SELECT * from CurrencyRateEntity LIMIT 1")
    fun fetchCurrencyRateEntity(): Observable<CurrencyRateEntity>
}