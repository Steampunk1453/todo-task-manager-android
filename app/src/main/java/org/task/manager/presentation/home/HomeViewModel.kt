package org.task.manager.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Book
import org.task.manager.domain.usecase.audiovisual.GetAudiovisual
import org.task.manager.domain.usecase.book.GetBook
import org.task.manager.presentation.shared.SharedViewModel

class HomeViewModel(
    private val getAudiovisual: GetAudiovisual,
    private val getBook: GetBook,
    private val sharedViewModel: SharedViewModel,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val audiovisual = MutableLiveData<Audiovisual>()
    val book = MutableLiveData<Book>()

    fun getAudiovisual(id: Long) {
        coroutineScope.launch {
            val audiovisualResult = getAudiovisual.execute(id)
            audiovisual.postValue(audiovisualResult)
        }
    }

    fun sendAudiovisual(audiovisual: Audiovisual) {
        sharedViewModel.sendAudiovisual(audiovisual)
    }

    fun getBook(id: Long) {
        coroutineScope.launch {
            val bookResult = getBook.execute(id)
            book.postValue(bookResult)
        }
    }

    fun sendBook(book: Book) {
        sharedViewModel.sendBook(book)
    }

}