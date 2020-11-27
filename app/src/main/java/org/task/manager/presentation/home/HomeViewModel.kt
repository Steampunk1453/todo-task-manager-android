package org.task.manager.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Book
import org.task.manager.domain.usecase.audiovisual.GetAudiovisuals
import org.task.manager.domain.usecase.book.GetBooks

class HomeViewModel(
    private val getAudiovisuals: GetAudiovisuals,
    private val getBooks: GetBooks,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val audiovisuals = MutableLiveData<List<Audiovisual>>()
    val books = MutableLiveData<List<Book>>()

    fun getAudiovisuals() {
        coroutineScope.launch {
            val audiovisualsResult = getAudiovisuals.execute()
            audiovisuals.postValue(audiovisualsResult)
        }
    }

    fun getBooks() {
        coroutineScope.launch {
            val booksResult = getBooks.execute()
            books.postValue(booksResult)
        }
    }

}