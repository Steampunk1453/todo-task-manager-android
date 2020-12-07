package org.task.manager.presentation.audiovisual

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.model.request.UserIdRequest
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
import org.task.manager.presentation.shared.DateService


class AudiovisualViewModel(
    private val getAudiovisuals: GetAudiovisuals,
    private val createAudiovisual: CreateAudiovisual,
    private val updateAudiovisual: UpdateAudiovisual,
    private val deleteAudiovisual: DeleteAudiovisual,
    private val getTitles: GetTitles,
    private val getGenres: GetGenres,
    private val getPlatforms: GetPlatforms,
    private val dateService: DateService,
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun createAudiovisual(title: String, genre: String, platform: String, platformUrl: String, startDate: Long,
                          deadline: Long, check: Int) {
        val audiovisualRequest = AudiovisualRequest(
            null,
            title,
            genre,
            platform,
            platformUrl,
            dateService.convertToInstant(startDate),
            dateService.convertToInstant(deadline),
            check,
            null
        )
        coroutineScope.launch {
            val audiovisualCreateResult = createAudiovisual.execute(audiovisualRequest)
            audiovisual.postValue(audiovisualCreateResult)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAudiovisual(id: Long, title: String, genre: String, platform: String, platformUrl: String,
                          startDate: Long, deadline: Long, check: Int, userId: Long) {
        val audiovisualRequest = AudiovisualRequest(
            id,
            title,
            genre,
            platform,
            platformUrl,
            dateService.convertToInstant(startDate),
            dateService.convertToInstant(deadline),
            check,
            UserIdRequest(userId)
        )
        coroutineScope.launch {
            val audiovisualUpdateResult = updateAudiovisual.execute(audiovisualRequest)
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