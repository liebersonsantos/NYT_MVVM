package com.example.nyt_mvvm.data.response

import com.example.nyt_mvvm.data.model.Book
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BooksDetails(

    @Json(name = "title")
    val title: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "description")
    val description: String
) {
    fun getBookModel() = Book(
        title = this.title,
        author = this.author,
        description = this.description
    )
}