package org.task.manager.data.network.api

import org.task.manager.data.network.model.response.AudiovisualResponse
import org.task.manager.shared.Constants
import retrofit2.Response
import retrofit2.http.GET

interface AudiovisualApi {

    @GET(Constants.AUDIOVISUAL_URL)
    suspend fun getAudiovisuals(): Response<List<AudiovisualResponse>>

}