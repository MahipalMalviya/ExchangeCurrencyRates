package com.mahipal.exchangecurrencyrates.model

fun Map<String,Double>.toDataEntity() = CurrencyRateEntity(
    id = 1,
    rates = this
)