package com.example.nyt_mvvm.presentation.books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nyt_mvvm.BuildConfig
import com.example.nyt_mvvm.R
import com.example.nyt_mvvm.data.ResultCallback
import com.example.nyt_mvvm.data.model.Book
import com.example.nyt_mvvm.data.repository.BooksRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var booksLiveDataObserver: Observer<List<Book>>

    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: BookViewModel

    //this is a way to initialize mockito
//    @Before
//    fun setup(){
//        MockitoAnnotations.initMocks(this)
//    }

    @Test
    fun `when viewmodel getBooks get success then sets booksLiveData`() {
        //arrange
        val books = listOf(Book("title 1", "author 1", "description 1"))
        val resultSuccess = MockBookRepositoryImpl(ResultCallback.Success(books))
        viewModel = BookViewModel(resultSuccess)
        viewModel.booksLiveData.observeForever(booksLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getBooks(BuildConfig.API_KEY, "hardcover-fiction")


        //assert
        verify(booksLiveDataObserver).onChanged(books)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))

    }

    @Test
    fun `when viewmodel getbooks get error 401 then sets viewFlipperLiveData`() {
        //arrange
        val resultError401 = MockBookRepositoryImpl(ResultCallback.ApiError(401))
        viewModel = BookViewModel(resultError401)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getBooks(BuildConfig.API_KEY, "hardcover-fiction")

        //assert
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.error_401))
    }

    @Test
    fun `when viewmodel getbooks get error 400 then sets viewFlipperLiveData`(){
        //arrange
        val resultError400 = MockBookRepositoryImpl(ResultCallback.ApiError(400))
        viewModel = BookViewModel(resultError400)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getBooks(BuildConfig.API_KEY, "hardcover-fiction")

        //assert
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.error_400_generic))
    }

    @Test
    fun `when viewmodel getbooks get servererror then sets viewFlipperLiveData`() {
        //arrange
        val resultServerError = MockBookRepositoryImpl(ResultCallback.ServerError)
        viewModel = BookViewModel(resultServerError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getBooks(BuildConfig.API_KEY, "hardcover-fiction")

        //assert
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.error_500_generic))

    }

}

class MockBookRepositoryImpl(private val result: ResultCallback) : BooksRepository {
    override fun getBooks(apiKey: String, listType: String, resultCallback: (result: ResultCallback) -> Unit) {
        resultCallback(result)
    }

}