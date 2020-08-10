package org.task.manager.presentation.audiovisual

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.usecase.GetAudiovisuals

class AudiovisualViewModel(private val getAudiovisuals: GetAudiovisuals) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val audiovisuals = MutableLiveData<List<Audiovisual>>()

    fun getAudiovisuals() {
        coroutineScope.launch {
            audiovisuals.postValue(getAudiovisuals.execute())
        }
    }

    public override fun onCleared() {
        coroutineScope.cancel()
    }

}