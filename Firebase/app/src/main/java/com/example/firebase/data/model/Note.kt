package com.example.firebase.data.model

data class Note(
    val id: String? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long = 0L // waktu dalam milidetik
)
