package org.task.manager.presentation.audiovisual

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.usecase.GetAudiovisuals

class AudiovisualViewModel(private val getAudiovisuals: GetAudiovisuals) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val audiovisuals = MutableLiveData<List<Audiovisual>>()
    val audiovisual = MutableLiveData<Audiovisual>()

    fun getAudiovisuals() {
        coroutineScope.launch {
            audiovisuals.postValue(getAudiovisuals.execute())
        }
    }

    fun createAudiovisual(audiovisualRequest: AudiovisualRequest) {
        coroutineScope.launch {

        }
    }

    public override fun onCleared() {
        coroutineScope.cancel()
    }

}