package com.example.nyt_mvvm.presentation.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nyt_mvvm.R
import com.example.nyt_mvvm.data.model.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BooksAdapter(
    val books: List<Book>,
    val itemClickListener: ((book: Book) -> Unit)
): RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): BooksViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BooksViewHolder(itemView, itemClickListener)
    }

    override fun getItemCount() = books.count()

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(books[position])
    }

    class BooksViewHolder(
        itemView: View,
        private val itemClickListener: ((book: Book) -> Unit)
    ): RecyclerView.ViewHolder(itemView){

        private val title = itemView.textTitle
        private val author = itemView.textAuthor

        fun bind(book: Book){
            title.text = book.title
            author.text = book.author

            itemView.setOnClickListener {
                itemClickListener.invoke(book)
            }
        }
    }
}