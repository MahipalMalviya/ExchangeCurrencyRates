package com.mahipal.exchangecurrencyrates.model

import androidx.room.*

@Entity(tableName = "CurrencyRateEntity")
data class CurrencyRateEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "rates")
    val rates: Map<String, Double>
)
