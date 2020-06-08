package com.example.nyt_mvvm.presentation.books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nyt_mvvm.BuildConfig
import com.example.nyt_mvvm.data.model.Book
import com.example.nyt_mvvm.data.response.BookResponse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call

@RunWith(MockitoJUnitRunner::class)
class BookViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private var repository = Mockito.mock(BookRepository::class.java)

    @Mock
    private lateinit var booksLiveDataObserver: Observer<List<Book>>

    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: BookViewModel

    @Test
    fun `when viewmodel getBooks get success then sets booksLiveData`() {
        //arrange
        val books: Call<BookResponse>
//        listOf(Book("title 1", "author 1", "description 1"))
        viewModel = BookViewModel()
        viewModel.booksLiveData.observeForever(booksLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        doReturn(books).`when`(repository).getBooks(BuildConfig.API_KEY, "hardcover-fiction")

        //act
        viewModel.getBooks()

        //assert
        verify(booksLiveDataObserver).onChanged(books)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))

    }

}