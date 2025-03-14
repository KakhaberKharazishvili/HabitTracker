package com.example.habittracker.data

import com.example.habittracker.data.local.Habit
import com.example.habittracker.data.local.HabitDao
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {

    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()

    suspend fun getHabitById(habitId: Int): Habit? {
        return habitDao.getHabitById(habitId)
    }

    suspend fun insertHabit(habit: Habit) {
        habitDao.insertHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }
}

