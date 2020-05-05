package com.example.nyt_mvvm.presentation.books

import com.example.nyt_mvvm.data.ApiService
import com.example.nyt_mvvm.data.response.BookResponse
import retrofit2.Call

class BookRepository {

    fun getBooks(apiKey: String, listType: String): Call<BookResponse> {
       return ApiService.service.getBooks(apiKey, listType)
    }
}