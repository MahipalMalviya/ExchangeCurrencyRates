package com.mahipal.exchangecurrencyrates.repository

import com.mahipal.exchangecurrencyrates.model.CurrencyRateResponse
import com.mahipal.exchangecurrencyrates.network.ServerApi
import dagger.Module
import io.reactivex.Observable
import javax.inject.Inject

@Module
class APIRepository @Inject constructor(private val serverApi: ServerApi) {

    private val API_KEY: String
        get() = "782f19abd99746d9a9cef14c785a9ed7"

    fun getCurrencyList(): Observable<Map<String,String>> {
        return serverApi.getCurrencyList(API_KEY)
    }

    fun getCurrencyRates(): Observable<CurrencyRateResponse> {
        return serverApi.getCurrencyRates(API_KEY)
    }
}