package com.example.habittracker.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.habittracker.data.local.Habit

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val habitsCollection = db.collection("habits")

    suspend fun uploadHabit(habit: Habit) {
        habitsCollection.document(habit.id.toString()).set(habit).await()
    }

    suspend fun deleteHabit(habitId: Int) {
        habitsCollection.document(habitId.toString()).delete().await()
    }

    suspend fun getHabits(): List<Habit> {
        val snapshot = habitsCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(Habit::class.java) }
    }
}
