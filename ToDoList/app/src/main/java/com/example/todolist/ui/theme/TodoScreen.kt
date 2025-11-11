package com.example.todolist.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.viewmodel.TodoViewModel
import com.example.todolist.model.FilterType
import com.example.todolist.model.Todo

@Composable
fun FilterButton(
    text: String,
    type: FilterType,
    current: FilterType,
    onClick: () -> Unit
) {
    val isSelected = type == current

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(32.dp)
            .padding(horizontal = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (isSelected) MaterialTheme.colorScheme.primary
                else Color(0xFF7A7680),
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(
            horizontal = 32.dp,
            vertical = 4.dp
        )
    ) {
        Text(
            text,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun TodoScreen(vm: TodoViewModel = viewModel()) {
    val todos by vm.filteredTodos.collectAsState()
    val filter by vm.filter.collectAsState()

    // Update task
    var showEditDialog by remember { mutableStateOf(false) }
    var editingTodo by remember { mutableStateOf<Todo?>(null) }
    var editText by remember { mutableStateOf("") }

    var text by rememberSaveable { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Tambah tugas...") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    vm.addTask(text.trim())
                    text = ""
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) { Text("Tambah") }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            FilterButton("All", FilterType.ALL, filter) { vm.setFilter(FilterType.ALL) }
            FilterButton("Active", FilterType.ACTIVE, filter) { vm.setFilter(FilterType.ACTIVE) }
            FilterButton("Done", FilterType.DONE, filter) { vm.setFilter(FilterType.DONE) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        LazyColumn {
            items(todos) { todo ->
                TodoItem(
                    todo = todo,
                    onToggle = { vm.toggleTask(todo.id) },
                    onDelete = { vm.deleteTask(todo.id) },
                    onEdit = {
                        editingTodo = todo
                        editText = todo.title
                        showEditDialog = true
                    }
                )
            }
        }
        if (showEditDialog && editingTodo != null) {
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Edit Task") },
                text = {
                    OutlinedTextField(
                        value = editText,
                        onValueChange = { editText = it },
                        label = { Text("Task name") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        vm.updateTask(editingTodo!!.id, editText.trim())
                        showEditDialog = false
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}