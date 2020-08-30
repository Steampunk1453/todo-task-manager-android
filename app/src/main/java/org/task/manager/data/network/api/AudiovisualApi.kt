package org.task.manager.data.network.api

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.model.response.AudiovisualResponse
import org.task.manager.shared.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AudiovisualApi {

    @GET(Constants.AUDIOVISUAL_URL)
    suspend fun getAudiovisuals(): Response<List<AudiovisualResponse>>

    @POST(Constants.AUDIOVISUAL_URL)
    suspend fun createAudiovisual(@Body request: AudiovisualRequest): Response<AudiovisualResponse>

    @DELETE(Constants.AUDIOVISUAL_URL + Constants.AUDIOVISUAL_ID)
    suspend fun deleteAudiovisual(@Path("id") id: Long): Response<Void>

}