package org.task.manager.presentation.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Book

class SharedViewModel : ViewModel() {
    val userData = MutableLiveData<String>()
    val audiovisual = MutableLiveData<Audiovisual>()
    val book = MutableLiveData<Book>()

    fun setUserName(username: String) {
        userData.value = username
    }

    fun sendAudiovisual(value: Audiovisual) {
        audiovisual.value = value
    }

    fun sendBook(value: Book) {
        book.value = value
    }

}