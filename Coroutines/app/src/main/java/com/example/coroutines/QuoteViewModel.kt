package com.example.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {
    private val repository = QuoteRepository()
    private val _quote = MutableStateFlow("Tap the button to get a quote")
    val quote: StateFlow<String> get() = _quote

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        _quote.value = "Terjadi kesalahan: ${throwable.message}"
    }

    fun fetchQuote() {
        viewModelScope.launch {
            _quote.value = "Loading..."
            val result = repository.getRandomQuote()
            _quote.value = result
        }
    }

    fun fetchQuoteWithThrow() {
        viewModelScope.launch(errorHandler) {
            _quote.value = "Loading..."
            try {
                val result = repository.getRandomQuote()
                throw IllegalStateException("Simulasi error setelah try–catch")
                }
            catch (e: Exception) {
                _quote.value = "Gagal memuat: ${e.message}"
                throw e
            }
        }
    }

}