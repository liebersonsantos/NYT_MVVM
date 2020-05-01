package com.example.nyt_mvvm.presentation.books

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nyt_mvvm.R
import com.example.nyt_mvvm.presentation.base.BaseActivity
import com.example.nyt_mvvm.presentation.details.BookDetailsActivity
import kotlinx.android.synthetic.main.activity_books.*
import kotlinx.android.synthetic.main.include_toolbar.*

class BooksActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        setupToolbar(toolbarMain, R.string.books_title)

        val viewModel: BookViewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)

        viewModel.booksLiveData.observe(this, Observer {

            it?.let { books ->
                with(recyclerBooks){
                    layoutManager = LinearLayoutManager(this@BooksActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = BooksAdapter(books) { book ->
                        val intent = BookDetailsActivity.getStartIntent(this@BooksActivity, book.title, book.description)
                       this@BooksActivity.startActivity(intent)

                    }

                }
            }

        })

        viewModel.isLoading.observe(this, Observer {
            if (it == true){
                progress.visibility = View.VISIBLE
            }else {
                progress.visibility = View.GONE
            }
        })

        viewModel.viewFlipperLiveData.observe(this, Observer {
            it?.let { flipper ->
                viewFlipper.displayedChild = flipper.first
                flipper.second?.let { errorMessageResId ->
                    textError.text = getString(errorMessageResId)

                }
            }

        })

        viewModel.getBooks()
    }

}
