package com.mahipal.exchangecurrencyrates.model

import com.squareup.moshi.Json

data class CurrencyRateResponse(
    @Json(name = "disclaimer") val disclaimer: String,
    @Json(name = "license") val license: String,
    @Json(name = "timestamp") val timestamp: Int,
    @Json(name = "base") val base: String,
    @Json(name = "rates") val rates: Map<String,Double>
)
