package org.task.manager.presentation.book

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.domain.model.*
import org.task.manager.domain.model.state.DeleteState
import org.task.manager.domain.usecase.book.*
import org.task.manager.domain.usecase.calendar.CreateCalendarEvent
import org.task.manager.domain.usecase.shared.GetGenres

class BookViewModel(
    private val getBooks: GetBooks,
    private val createBook: CreateBook,
    private val updateBook: UpdateBook,
    private val deleteBook: DeleteBook,
    private val getGenres: GetGenres,
    private val getBookshops: GetBookshops,
    private val getEditorials: GetEditorials,
    private val createCalendarEvent: CreateCalendarEvent
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val books = MutableLiveData<List<Book>>()
    val book = MutableLiveData<Book?>()
    val genres = MutableLiveData<List<Genre>>()
    val bookshops = MutableLiveData<List<Bookshop>>()
    val editorials = MutableLiveData<List<Editorial>>()
    val calendarEvents = MutableLiveData<Intent>()
    val deleteState = MutableLiveData(DeleteState.DELETE_STARTED)

    fun getBooks() {
        coroutineScope.launch {
            val booksResult = getBooks.execute()
            books.postValue(booksResult)
        }
    }

    fun createBook(bookDto: BookDto) {
        val newBook = bookDto.toDomain()
        coroutineScope.launch {
            val bookCreateResult = createBook.execute(newBook)
            book.postValue(bookCreateResult)
        }
    }

    fun updateBook(bookDto: BookDto) {
        val updatedBook = bookDto.toDomain()
        coroutineScope.launch {
            val bookUpdatedResult = updateBook.execute(updatedBook)
            book.postValue(bookUpdatedResult)
        }
    }

    fun deleteBook(id: Long) {
        coroutineScope.launch {
            val deleteResult = deleteBook.execute(id)
            deleteState.postValue(deleteResult)
        }
    }

    fun getGenres() {
        coroutineScope.launch {
            val genresResult = getGenres.execute()
            genres.postValue(genresResult)
        }
    }

    fun getBookshops() {
        coroutineScope.launch {
            val bookshopsResult = getBookshops.execute()
            bookshops.postValue(bookshopsResult)
        }
    }

    fun getEditorials() {
        coroutineScope.launch {
            val editorialsResult = getEditorials.execute()
            editorials.postValue(editorialsResult)
        }
    }

    fun createCalendarEvent(beginTime: Long, title: String, description: String) {
        val calendarEvent = CalendarEvent(
            beginTime,
            title,
            description
        )
        coroutineScope.launch {
            val calendarEventResult = createCalendarEvent.execute(calendarEvent)
            calendarEvents.postValue(calendarEventResult)
        }
    }

    public override fun onCleared() {
        coroutineScope.cancel()
    }

}