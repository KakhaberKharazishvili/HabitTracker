package com.example.habittracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int, Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var frequencyText by remember { mutableStateOf("1") }
    var isPinned by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить привычку") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Описание") }
                )
                TextField(
                    value = frequencyText,
                    onValueChange = { frequencyText = it.filter { char -> char.isDigit() } },
                    label = { Text("Частота (раз в неделю)") }
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Закрепить привычку")
                    Switch(checked = isPinned, onCheckedChange = { isPinned = it })
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val frequency = frequencyText.toIntOrNull() ?: 1
                if (name.isNotBlank() && description.isNotBlank()) {
                    onConfirm(name, description, frequency, isPinned)
                }
            }) {
                Text("Добавить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}
