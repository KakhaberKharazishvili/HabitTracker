package com.example.habittracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.data.HabitRepository
import com.example.habittracker.data.FirebaseRepository
import com.example.habittracker.data.local.AppDatabase
import com.example.habittracker.viewmodel.HabitViewModel
import com.example.habittracker.viewmodel.HabitViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val habitDao = database.habitDao()
        val habitRepository = HabitRepository(habitDao)
        val firebaseRepository = FirebaseRepository()

        val viewModel = ViewModelProvider(
            this,
            HabitViewModelFactory(habitRepository, firebaseRepository)
        ).get(HabitViewModel::class.java)

        setContent {
            HabitAppScreen(viewModel = viewModel)
        }
    }
}
