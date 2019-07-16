package com.example.nyt_mvvm.presentation.books

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nyt_mvvm.data.ApiService
import com.example.nyt_mvvm.data.model.Book
import com.example.nyt_mvvm.data.response.BookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel: ViewModel() {

    val booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()

    fun getBooks(){
//        booksLiveData.value = createFakeBooks()
        ApiService.service.getBooks().enqueue(object: Callback<BookResponse>{
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful){
                    val books: MutableList<Book> = mutableListOf()

                    response.body()?.let { bookResponse ->
                        for (result in bookResponse.booksResults){
                            val book = Book(
                                title = result.bookDetails[0].title,
                                author = result.bookDetails[0].author,
                                description = result.bookDetails[0].description
                            )

                            books.add(book)
                        }
                    }

                    booksLiveData.value = books
                }

            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.d("VIEW_MODEL", "error --> " + t.message)

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
}