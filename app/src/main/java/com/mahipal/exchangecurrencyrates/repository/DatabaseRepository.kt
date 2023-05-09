package com.mahipal.exchangecurrencyrates.repository

import com.mahipal.exchangecurrencyrates.di.database.CurrencyRateDao
import com.mahipal.exchangecurrencyrates.model.CurrencyRateEntity
import com.mahipal.exchangecurrencyrates.model.toDataEntity
import dagger.Module
import javax.inject.Inject

@Module
class DatabaseRepository @Inject constructor() {

    fun insertCurrencyData(dao: CurrencyRateDao,entity: CurrencyRateEntity) {
        dao.insertCurrencyRate(entity)
    }

    fun getCurrencyRateData(dao: CurrencyRateDao) = dao.fetchCurrencyRateEntity()
}