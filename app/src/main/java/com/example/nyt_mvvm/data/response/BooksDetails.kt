package com.example.nyt_mvvm.data.response

import com.example.nyt_mvvm.data.model.Book
import com.google.gson.annotations.SerializedName

data class BooksDetails(

    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String
) {
    fun getBookModel() = Book(
        title = this.title,
        author = this.author,
        description = this.description
    )
}