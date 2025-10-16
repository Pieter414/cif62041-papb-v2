package com.example.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {
    private val repository = QuoteRepository()
    private val _quote = MutableStateFlow("Tap the button to get a quote")
    val quote: StateFlow<String> get() = _quote
    fun fetchQuote() {
        viewModelScope.launch(Dispatchers.Main) {
            _quote.value = "Loading..."
            val result = repository.getRandomQuote()
            _quote.value = result
        }
    }
}