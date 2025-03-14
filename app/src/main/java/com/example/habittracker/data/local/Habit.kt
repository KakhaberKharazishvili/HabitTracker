package com.example.habittracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val frequency: Int,
    val completedDays: List<Long> = emptyList(),
    val isPinned: Boolean = false
) {
    fun streak(): Int {
        if (completedDays.isEmpty()) return 0
        val sortedDays = completedDays.sortedDescending()
        var streak = 1
        for (i in 1 until sortedDays.size) {
            if (sortedDays[i] == sortedDays[i - 1] - 1) {
                streak++
            } else {
                break
            }
        }
        return streak
    }

    fun completionRate(): Int {
        val totalDays = frequency * 4 // Допустим, 4 недели в месяце
        return if (totalDays == 0) 0 else (completedDays.size * 100) / totalDays
    }
}
