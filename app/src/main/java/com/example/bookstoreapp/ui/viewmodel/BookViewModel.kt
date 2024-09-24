package com.example.bookstoreapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstoreapp.network.Book
import com.example.bookstoreapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    // Thay đổi từ mutableStateOf sang MutableStateFlow
    private val _bookList = MutableStateFlow<List<Book>>(emptyList())
    val bookList: StateFlow<List<Book>> = _bookList

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getBooks().execute()
                _bookList.value = response.body() ?: emptyList()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
