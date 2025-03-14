package com.example.habittracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittracker.data.local.Habit
import com.example.habittracker.viewmodel.HabitViewModel

@Composable
fun HabitAppScreen(viewModel: HabitViewModel) {
    val habits by viewModel.habits.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Habit Tracker") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            HabitList(habits, viewModel)
        }
    }

    if (showDialog) {
        AddHabitDialog(
            onDismiss = { showDialog = false },
            onConfirm = { name, description, frequency, isPinned ->
                viewModel.addHabit(
                    com.example.habittracker.data.local.Habit(
                        name = name,
                        description = description,
                        frequency = frequency,
                        completedDays = emptyList(),
                        isPinned = isPinned
                    )
                )
                showDialog = false
            }
        )
    }
}



@Composable
fun HabitList(habits: List<Habit>, viewModel: HabitViewModel) {
    val pinnedHabits = habits.filter { it.isPinned }
    val normalHabits = habits.filter { !it.isPinned }

    LazyColumn {
        if (pinnedHabits.isNotEmpty()) {
            item {
                Text(
                    text = "📌 Закрепленные привычки",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(pinnedHabits, key = { it.id }) { habit ->
                HabitItem(habit) { viewModel.deleteHabit(habit) }
            }
        }

        if (normalHabits.isNotEmpty()) {
            item {
                Text(
                    text = "🌱 Обычные привычки",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(normalHabits, key = { it.id }) { habit ->
                HabitItem(habit) { viewModel.deleteHabit(habit) }
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit, onDelete: (Habit) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        backgroundColor = if (habit.isPinned) MaterialTheme.colors.primary.copy(alpha = 0.1f) else MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "Описание: ${habit.description}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "🔥 Стрик: ${habit.streak()} дней подряд",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "📊 Прогресс: ${habit.completionRate()}%",
                style = MaterialTheme.typography.body2
            )

            if (habit.isPinned) {
                Text(
                    text = "📌 Закреплено",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onDelete(habit) },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Text("Удалить", color = MaterialTheme.colors.onError)
            }
        }
    }
}
