package org.task.manager.presentation.book

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_audiovisual.view.checkBox
import kotlinx.android.synthetic.main.item_audiovisual.view.deadline
import kotlinx.android.synthetic.main.item_audiovisual.view.genre
import kotlinx.android.synthetic.main.item_audiovisual.view.startDate
import kotlinx.android.synthetic.main.item_audiovisual.view.title
import kotlinx.android.synthetic.main.item_book.view.author
import kotlinx.android.synthetic.main.item_book.view.bookshop
import kotlinx.android.synthetic.main.item_book.view.editorial
import org.task.manager.R
import org.task.manager.domain.model.Book
import org.task.manager.presentation.shared.DateService
import org.task.manager.presentation.shared.SharedViewModel
import org.task.manager.shared.Constants.TRUE

class BookAdapter(private val books: List<Book>,
                  private val bookViewModel: BookViewModel,
                  private val sharedViewModel: SharedViewModel,
                  private val dateService : DateService
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_book, parent, false)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        populateItemView(holder, position)

        holder.itemView.setOnLongClickListener {
            bookViewModel.getBook(books[position].id)

            bookViewModel.book.observe(holder.itemView.context as LifecycleOwner, Observer {
                sharedViewModel.sendBook(it)

                val bundle = bundleOf("action" to "update")
                Navigation.findNavController(holder.itemView).navigate(R.id.fragment_create_book, bundle)
            })
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount() = books.size

    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateItemView(holder: ViewHolder, position: Int) {
        holder.itemView.title.text = books[position].title
        holder.itemView.author.text = books[position].author
        holder.itemView.genre.text = books[position].genre
        holder.itemView.editorial.text = books[position].editorial
        holder.itemView.bookshop.text = books[position].bookshop
        holder.itemView.startDate.text = dateService.getFormattedDate(books[position].startDate)
        holder.itemView.deadline.text = dateService.getFormattedDate(books[position].deadline)
        holder.itemView.checkBox.isChecked = books[position].check == TRUE
    }

}

