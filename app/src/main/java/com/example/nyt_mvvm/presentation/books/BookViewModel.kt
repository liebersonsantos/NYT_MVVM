package com.example.nyt_mvvm.presentation.books

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nyt_mvvm.R
import com.example.nyt_mvvm.data.ApiService
import com.example.nyt_mvvm.data.model.Book
import com.example.nyt_mvvm.data.response.BookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel: ViewModel() {

    val booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getBooks(){
//        booksLiveData.value = createFakeBooks()
        ApiService.service.getBooks().enqueue(object: Callback<BookResponse>{
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                when {
                    response.isSuccessful -> {
                        val books: MutableList<Book> = mutableListOf()

                        response.body()?.let { bookResponse ->
                            for (result in bookResponse.booksResults){
                                val book = result.bookDetails[0].getBookModel()

                                books.add(book)
                            }
                        }

                        booksLiveData.value = books
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_BOOKS, null)
                    }
                    response.code() == 401 -> viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_401)
                    else -> viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_400_generic)
                }

            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.d("VIEW_MODEL", "error --> " + t.message)
                viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_500_generic )
            }

        })

    }

    fun createFakeBooks(): List<Book>{
        return listOf(
            Book("Title 1", "Author 1", "Description 1"),
            Book("Title 2", "Author 2", "Description 2"),
            Book("Title 3", "Author 3", "Description 3"),
            Book("Title 4", "Author 4", "Description 4")
        )
    }

    companion object{
        private const val VIEW_FLIPPER_BOOKS = 1
        private const val VIEW_FLIPPER_ERROR = 2

    }
}