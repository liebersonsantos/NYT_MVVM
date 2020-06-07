package com.example.nyt_mvvm.data

import com.example.nyt_mvvm.data.model.Book

sealed class ResultCallback {
    class Success(val books: List<Book>) : ResultCallback()
    class ApiError(val statusCode: Int) : ResultCallback()
    object ServerError : ResultCallback()
}