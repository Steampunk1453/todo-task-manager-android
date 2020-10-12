package org.task.manager.presentation.book

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.BookRequest
import org.task.manager.data.network.model.request.UserRequest
import org.task.manager.domain.model.Book
import org.task.manager.domain.model.Bookshop
import org.task.manager.domain.model.Editorial
import org.task.manager.domain.model.Genre
import org.task.manager.domain.usecase.book.CreateBook
import org.task.manager.domain.usecase.book.DeleteBook
import org.task.manager.domain.usecase.book.GetBook
import org.task.manager.domain.usecase.book.GetBooks
import org.task.manager.domain.usecase.book.GetBookshops
import org.task.manager.domain.usecase.book.GetEditorials
import org.task.manager.domain.usecase.book.UpdateBook
import org.task.manager.domain.usecase.shared.GetGenres
import org.task.manager.presentation.shared.DateService


class BookViewModel(
    private val getBooks: GetBooks,
    private val createBook: CreateBook,
    private val updateBook: UpdateBook,
    private val deleteBook: DeleteBook,
    private val getBook: GetBook,
    private val getGenres: GetGenres,
    private val getBookshops: GetBookshops,
    private val getEditorials: GetEditorials,
    private val dateService: DateService
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val books = MutableLiveData<List<Book>>()
    val book = MutableLiveData<Book>()
    val genres = MutableLiveData<List<Genre>>()
    val bookshops = MutableLiveData<List<Bookshop>>()
    val editorials = MutableLiveData<List<Editorial>>()

    init {
        coroutineScope.launch {
            val booksResult = getBooks.execute()
            books.postValue(booksResult)
            val genresResult = getGenres.execute()
            genres.postValue(genresResult)
            val bookshopsResult = getBookshops.execute()
            bookshops.postValue(bookshopsResult)
            val editorialsResult = getEditorials.execute()
            editorials.postValue(editorialsResult)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createBook(title: String, author: String, genre: String, editorial: String, editorialUrl: String,
                   bookshop: String,  bookshopUrl: String, startDate: Long, deadline: Long, check: Int) {
        val bookRequest = BookRequest(
            null,
            title,
            author,
            genre,
            editorial,
            editorialUrl,
            bookshop,
            bookshopUrl,
            dateService.convertToInstant(startDate),
            dateService.convertToInstant(deadline),
            check,
            null
        )
        coroutineScope.launch {
            val bookCreateResult = createBook.execute(bookRequest)
            book.postValue(bookCreateResult)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateBook(id: Long, title: String, author: String, genre: String, editorial: String, editorialUrl: String,
                   bookshop: String,  bookshopUrl: String, startDate: Long, deadline: Long, check: Int, userId: Long) {
        val bookRequest = BookRequest(
            id,
            title,
            author,
            genre,
            editorial,
            editorialUrl,
            bookshop,
            bookshopUrl,
            dateService.convertToInstant(startDate),
            dateService.convertToInstant(deadline),
            check,
            UserRequest(userId)
        )
        coroutineScope.launch {
            val audiovisualUpdateResult = updateBook.execute(bookRequest)
            book.postValue(audiovisualUpdateResult)
        }
    }

    fun getBook(id: Long) {
        coroutineScope.launch {
            val bookResult = getBook.execute(id)
            book.postValue(bookResult)
        }
    }

    fun deleteBook(id: Long) {
        coroutineScope.launch {
            deleteBook.execute(id)
        }
    }

    public override fun onCleared() {
        coroutineScope.cancel()
    }

}