package com.example.nyt_mvvm.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookResponse (

    @Json(name = "results")
    val booksResults: List<BooksResults>
)