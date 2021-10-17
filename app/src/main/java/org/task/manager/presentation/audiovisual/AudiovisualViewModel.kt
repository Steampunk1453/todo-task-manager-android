package org.task.manager.presentation.audiovisual

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.domain.model.*
import org.task.manager.domain.model.state.DeleteState
import org.task.manager.domain.usecase.audiovisual.*
import org.task.manager.domain.usecase.calendar.CreateCalendarEvent
import org.task.manager.domain.usecase.shared.GetGenres

class AudiovisualViewModel(
    private val getAudiovisuals: GetAudiovisuals,
    private val createAudiovisual: CreateAudiovisual,
    private val updateAudiovisual: UpdateAudiovisual,
    private val deleteAudiovisual: DeleteAudiovisual,
    private val getTitles: GetTitles,
    private val getGenres: GetGenres,
    private val getPlatforms: GetPlatforms,
    private val createCalendarEvent: CreateCalendarEvent
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val audiovisuals = MutableLiveData<List<Audiovisual>>()
    val audiovisual = MutableLiveData<Audiovisual?>()
    val titles = MutableLiveData<List<Title>>()
    val genres = MutableLiveData<List<Genre>>()
    val platforms = MutableLiveData<List<Platform>>()
    val calendarEvents = MutableLiveData<Intent>()
    val deleteState = MutableLiveData(DeleteState.DELETE_STARTED)

    fun getAudiovisuals() {
        coroutineScope.launch {
            val audiovisualsResult = getAudiovisuals.execute()
            audiovisuals.postValue(audiovisualsResult)
        }
    }

    fun createAudiovisual(audiovisualDto: AudiovisualDto) {
        val newAudiovisual = audiovisualDto.toDomain()
        coroutineScope.launch {
            val audiovisualCreateResult = createAudiovisual.execute(newAudiovisual)
            audiovisual.postValue(audiovisualCreateResult)
        }
    }

    fun updateAudiovisual(audiovisualDto: AudiovisualDto) {
        val updatedBook = audiovisualDto.toDomain()
        coroutineScope.launch {
            val audiovisualUpdateResult = updateAudiovisual.execute(updatedBook)
            audiovisual.postValue(audiovisualUpdateResult)
        }

    }

    fun deleteAudiovisual(id: Long) {
        coroutineScope.launch {
            val deleteResult = deleteAudiovisual.execute(id)
            deleteState.postValue(deleteResult)
        }
    }

    fun getTitles(type: String) {
        coroutineScope.launch {
            val titlesResult = getTitles.execute(type)
            titles.postValue(titlesResult)
        }
    }

    fun getGenres() {
        coroutineScope.launch {
            val genresResult = getGenres.execute()
            genres.postValue(genresResult)
        }
    }

    fun getPlatforms() {
        coroutineScope.launch {
            val platformsResult = getPlatforms.execute()
            platforms.postValue(platformsResult)
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