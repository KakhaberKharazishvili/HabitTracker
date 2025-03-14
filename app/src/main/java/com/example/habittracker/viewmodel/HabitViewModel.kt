package com.example.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.FirebaseRepository
import com.example.habittracker.data.HabitRepository
import com.example.habittracker.data.local.Habit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository, private val firebaseRepository: FirebaseRepository) : ViewModel() {
    fun syncWithFirebase() {
        viewModelScope.launch {
            val firebaseHabits = firebaseRepository.getHabits()
            firebaseHabits.forEach { repository.insertHabit(it) }
        }
    }

    val habits: StateFlow<List<Habit>> = repository.allHabits
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.insertHabit(habit)
            firebaseRepository.uploadHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
            firebaseRepository.deleteHabit(habit.id)


        }
    }
}

class HabitViewModelFactory(
    private val habitRepository: HabitRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(habitRepository, firebaseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}