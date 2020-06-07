package com.example.nyt_mvvm.data.repository

import com.example.nyt_mvvm.data.ResultCallback

interface BooksRepository {
    fun getBooks(apiKey: String, listType: String, resultCallback: (result: ResultCallback) -> Unit)
}