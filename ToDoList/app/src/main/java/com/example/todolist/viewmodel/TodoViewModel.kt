package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.todolist.model.Todo
import com.example.todolist.model.FilterType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class TodoViewModel : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    private val _filter = MutableStateFlow(FilterType.ALL)

    val filter: StateFlow<FilterType> = _filter
    val todos: StateFlow<List<Todo>> = _todos

    fun setFilter(type: FilterType) {
        _filter.value = type
    }

    fun addTask(title: String) {
        val nextId = (_todos.value.maxOfOrNull { it.id } ?: 0) + 1
        val newTask = Todo(id = nextId, title = title)
        _todos.value = _todos.value + newTask
    }
    fun toggleTask(id: Int) {
        _todos.value = _todos.value.map { t ->
            if (t.id == id) t.copy(isDone = !t.isDone) else t
        }
    }
    fun deleteTask(id: Int) {
        _todos.value = _todos.value.filterNot { it.id == id }
    }

    val filteredTodos: StateFlow<List<Todo>> =
        combine(_todos, _filter) { todos, filter ->
            when (filter) {
                FilterType.ALL -> todos
                FilterType.ACTIVE -> todos.filter { !it.isDone }
                FilterType.DONE -> todos.filter { it.isDone }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    fun updateTask(id: Int, newTitle: String) {
        _todos.value = _todos.value.map { t ->
            if (t.id == id) t.copy(title = newTitle) else t
        }
    }
}
