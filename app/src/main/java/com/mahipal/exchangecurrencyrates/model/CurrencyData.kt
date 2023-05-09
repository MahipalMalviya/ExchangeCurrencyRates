package com.mahipal.exchangecurrencyrates.model

data class CurrencyData(
    val amount: Double,
    val rates: Map<String, Double>
)
