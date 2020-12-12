package org.task.manager.presentation.audiovisual

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.CalendarEvent
import org.task.manager.domain.model.Genre
import org.task.manager.domain.model.Platform
import org.task.manager.domain.model.Title
import org.task.manager.domain.model.state.DeleteState
import org.task.manager.domain.usecase.audiovisual.CreateAudiovisual
import org.task.manager.domain.usecase.audiovisual.DeleteAudiovisual
import org.task.manager.domain.usecase.audiovisual.GetAudiovisuals
import org.task.manager.domain.usecase.audiovisual.GetPlatforms
import org.task.manager.domain.usecase.audiovisual.GetTitles
import org.task.manager.domain.usecase.audiovisual.UpdateAudiovisual
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
    val audiovisual = MutableLiveData<Audiovisual>()
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
            val bookCreateResult = createAudiovisual.execute(newAudiovisual)
            audiovisual.postValue(bookCreateResult)
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

    fun getTitles() {
        coroutineScope.launch {
            val titlesResult = getTitles.execute()
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