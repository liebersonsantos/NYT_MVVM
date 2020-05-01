package com.example.nyt_mvvm.data.response

import com.google.gson.annotations.SerializedName

data class BooksResults (

    @SerializedName("book_details")
    val bookDetails: List<BooksDetails>
)