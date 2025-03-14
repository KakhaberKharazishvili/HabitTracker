package com.example.habittracker

import android.app.Application
import com.example.habittracker.data.HabitRepository
import com.example.habittracker.data.local.HabitDatabase

class HabitApp : Application() {

    // Создаем репозиторий в качестве глобального объекта
    val repository: HabitRepository by lazy {
        val database = HabitDatabase.getDatabase(this)
        HabitRepository(database.habitDao())
    }
}
