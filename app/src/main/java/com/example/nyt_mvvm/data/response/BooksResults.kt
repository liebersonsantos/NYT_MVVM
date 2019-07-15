package com.example.nyt_mvvm.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BooksResults (

    @Json(name = "book_details")
    val bookDetails: List<BooksDetails>
)