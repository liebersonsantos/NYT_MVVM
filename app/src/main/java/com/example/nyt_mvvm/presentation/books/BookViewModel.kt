package com.example.nyt_mvvm.presentation.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nyt_mvvm.R
import com.example.nyt_mvvm.data.ResultCallback
import com.example.nyt_mvvm.data.model.Book
import com.example.nyt_mvvm.data.repository.BooksRepository
import java.lang.IllegalArgumentException

class BookViewModel(val repository: BooksRepository) : ViewModel() {

    val booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getBooks(apiKey: String, listType: String){
        repository.getBooks(apiKey, listType){ callback: ResultCallback ->
            when(callback){
                is ResultCallback.Success -> {
                    booksLiveData.value = callback.books
                    viewFlipperLiveData.value = Pair(VIEW_FLIPPER_BOOKS, null)
                }
                is ResultCallback.ApiError -> {
                    if (callback.statusCode == 401){
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_401)
                    }else{
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_400_generic)
                    }
                }
                is ResultCallback.ServerError -> viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_500_generic)
            }
        }
    }

    class ViewModelFactory(val repository: BooksRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookViewModel::class.java)){
                return BookViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

    fun createFakeBooks(): List<Book> {
        return listOf(
            Book("Title 1", "Author 1", "Description 1"),
            Book("Title 2", "Author 2", "Description 2"),
            Book("Title 3", "Author 3", "Description 3"),
            Book("Title 4", "Author 4", "Description 4")
        )
    }

    companion object {
        private const val VIEW_FLIPPER_BOOKS = 1
        private const val VIEW_FLIPPER_ERROR = 2
        private const val LIST_TYPE = "hardcover-fiction"

    }
}