package com.mahipal.exchangecurrencyrates.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapConverter {
    @TypeConverter
    fun toMap(value: String): Map<String,Double> {
        return Gson().fromJson(value, object : TypeToken<Map<String,Double>>() {}.type)
    }

    @TypeConverter
    fun fromMap(map: Map<String,Double>): String? {
        return Gson().toJson(map)
    }
}