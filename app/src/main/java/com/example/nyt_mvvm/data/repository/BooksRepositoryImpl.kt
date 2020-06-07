package com.example.nyt_mvvm.data.repository

import android.util.Log
import com.example.nyt_mvvm.data.ApiService
import com.example.nyt_mvvm.data.ResultCallback
import com.example.nyt_mvvm.data.model.Book
import com.example.nyt_mvvm.data.response.BookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksRepositoryImpl: BooksRepository {

    override fun getBooks(apiKey: String, listType: String, resultCallback: (result: ResultCallback) -> Unit) {
        ApiService.service.getBooks(apiKey, listType).enqueue(object : Callback<BookResponse>{
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                when {
                    response.isSuccessful -> {
                        val books: MutableList<Book> = mutableListOf()

                        response.body()?.let { bookResponse ->
                            for (result in bookResponse.booksResults) {
                                val book = result.bookDetails[0].getBookModel()

                                books.add(book)
                            }
                        }

                        resultCallback(ResultCallback.Success(books))
                    }
                    response.code() == 401 -> resultCallback(ResultCallback.ApiError(401))
                    else -> resultCallback(ResultCallback.ApiError(400))
                }

            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.d("VIEW_MODEL", "error --> " + t.message)
                resultCallback(ResultCallback.ServerError)
            }
        })

    }

}