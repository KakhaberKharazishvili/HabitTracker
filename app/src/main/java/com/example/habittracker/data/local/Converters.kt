package com.example.habittracker.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    // Конвертер для List<Long>
    @TypeConverter
    fun fromLongList(value: String?): List<Long> {
        if (value == null) return emptyList()
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toLongList(list: List<Long>?): String {
        return gson.toJson(list)
    }
}
