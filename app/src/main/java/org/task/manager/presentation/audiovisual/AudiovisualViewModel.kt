package org.task.manager.presentation.audiovisual

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.usecase.CreateAudiovisual
import org.task.manager.domain.usecase.DeleteAudiovisual
import org.task.manager.domain.usecase.GetAudiovisuals
import java.sql.Timestamp


class AudiovisualViewModel(
    private val getAudiovisuals: GetAudiovisuals,
    private val createAudiovisual: CreateAudiovisual,
    private val deleteAudiovisual: DeleteAudiovisual
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val audiovisuals = MutableLiveData<List<Audiovisual>>()
    val audiovisual = MutableLiveData<Audiovisual>()

    fun getAudiovisuals() {
        coroutineScope.launch {
            audiovisuals.postValue(getAudiovisuals.execute())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createAudiovisual(
        title: String, genre: String, platform: String, startDate: Long,
        deadline: Long, check: Int
    ) {
        val audiovisualRequest = AudiovisualRequest(
            title,
            genre,
            platform,
            convertToInstantString(startDate),
            convertToInstantString(deadline),
            check
        )
        coroutineScope.launch {
            audiovisual.postValue(createAudiovisual.execute(audiovisualRequest))
        }
    }

    fun deleteAudiovisual(id: Long) {
        coroutineScope.launch {
            deleteAudiovisual.execute(id)
        }
    }

    public override fun onCleared() {
        coroutineScope.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToInstantString(dateMilliseconds: Long): String {
        return Timestamp(dateMilliseconds).toInstant().toString()
    }

}