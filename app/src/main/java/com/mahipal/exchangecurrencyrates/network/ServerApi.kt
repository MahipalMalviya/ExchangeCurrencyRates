package com.mahipal.exchangecurrencyrates.network

import com.mahipal.exchangecurrencyrates.model.CurrencyRateResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("currencies.json")
    fun getCurrencyList(@Query("app_id") apiKey: String): Observable<Map<String,String>>

    @GET("latest.json")
    fun getCurrencyRates(@Query("app_id") apiKey: String): Observable<CurrencyRateResponse>

}