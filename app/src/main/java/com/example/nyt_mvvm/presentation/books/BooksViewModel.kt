package com.example.nyt_mvvm.presentation.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nyt_mvvm.data.model.Book

class BooksViewModel: ViewModel() {

    val booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()

    fun getBooks(){
        booksLiveData.value = createFakeBooks()
    }

    fun createFakeBooks(): List<Book>{
        return listOf(
            Book("Title 1", "Author 1"),
            Book("Title 2", "Author 2"),
            Book("Title 3", "Author 3"),
            Book("Title 4", "Author 4")
        )
    }
}