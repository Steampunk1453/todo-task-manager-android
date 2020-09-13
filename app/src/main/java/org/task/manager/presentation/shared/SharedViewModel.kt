package org.task.manager.presentation.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.task.manager.domain.model.Audiovisual

class SharedViewModel : ViewModel() {
    val userData = MutableLiveData<String>()
    val audiovisual = MutableLiveData<Audiovisual>()

    fun setUserName(username: String) {
        userData.value = username
    }

    fun sendAudiovisual(obj: Audiovisual) {
        audiovisual.value = obj
    }

}