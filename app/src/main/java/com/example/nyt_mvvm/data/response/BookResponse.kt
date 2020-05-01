package com.example.nyt_mvvm.data.response

import com.google.gson.annotations.SerializedName

data class BookResponse(

    @SerializedName("results")
    val booksResults: List<BooksResults>
)