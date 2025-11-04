package com.example.dataroom.task

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    // Fungsi untuk Read: Room sudah menyediakan Flow
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    // Fungsi untuk Create: dipanggil dalam Coroutine
    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }
    // Fungsi untuk Update: dipanggil dalam Coroutine
    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }
    // Fungsi untuk Delete: dipanggil dalam Coroutine
    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }
    // Fungsi untuk updateTaskTitle: dipanggil dalam Coroutine
    suspend fun updateTaskTitle(task: Task, newTitle:
    String) {
        val updatedTask = task.copy(title = newTitle)
        taskDao.updateTask(updatedTask)
    }
}