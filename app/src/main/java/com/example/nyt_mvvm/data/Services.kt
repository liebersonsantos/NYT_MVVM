package com.example.nyt_mvvm.data

import com.example.nyt_mvvm.data.response.BookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("lists.json")
    fun getBooks(@Query("api-key") apiKey: String = "8XnL5GAX4HvAkNnLMFXBkfU3vKRlG87q",
                 @Query("list") list: String = "hardcover-fiction"
    ): Call<BookResponse>
}
