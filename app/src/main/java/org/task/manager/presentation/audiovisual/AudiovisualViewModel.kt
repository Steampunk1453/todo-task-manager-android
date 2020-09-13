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
import org.task.manager.data.network.model.request.UserRequest
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.usecase.CreateAudiovisual
import org.task.manager.domain.usecase.DeleteAudiovisual
import org.task.manager.domain.usecase.GetAudiovisual
import org.task.manager.domain.usecase.GetAudiovisuals
import org.task.manager.domain.usecase.UpdateAudiovisual
import java.sql.Timestamp


class AudiovisualViewModel(
    private val getAudiovisuals: GetAudiovisuals,
    private val createAudiovisual: CreateAudiovisual,
    private val updateAudiovisual: UpdateAudiovisual,
    private val deleteAudiovisual: DeleteAudiovisual,
    private val getAudiovisual: GetAudiovisual
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val audiovisuals = MutableLiveData<List<Audiovisual>>()
    val audiovisual = MutableLiveData<Audiovisual>()

    init {
        coroutineScope.launch {
            audiovisuals.postValue(getAudiovisuals.execute())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createAudiovisual(
        title: String, genre: String, platform: String, startDate: Long, deadline: Long, check: Int) {
        val audiovisualRequest = AudiovisualRequest(
            null,
            title,
            genre,
            platform,
            convertToInstantString(startDate),
            convertToInstantString(deadline),
            check,
            null
        )
        coroutineScope.launch {
            audiovisual.postValue(createAudiovisual.execute(audiovisualRequest))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAudiovisual(id: Long, title: String, genre: String, platform: String, startDate: Long,
                          deadline: Long, check: Int, userId: Long) {
        val audiovisualRequest = AudiovisualRequest(
            id,
            title,
            genre,
            platform,
            convertToInstantString(startDate),
            convertToInstantString(deadline),
            check,
            UserRequest(userId)
        )
        coroutineScope.launch {
            audiovisual.postValue(updateAudiovisual.execute(audiovisualRequest))
        }
    }

    fun getAudiovisual(id: Long) {
        coroutineScope.launch {
            audiovisual.postValue(getAudiovisual.execute(id))
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