package org.task.manager.data.network.api

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.model.response.AudiovisualResponse
import org.task.manager.data.network.model.response.PlatformResponse
import org.task.manager.data.network.model.response.TitleResponse
import org.task.manager.shared.Constants
import retrofit2.Response
import retrofit2.http.*

interface AudiovisualApi {

    @POST(Constants.AUDIOVISUAL_URL)
    suspend fun createAudiovisual(@Body request: AudiovisualRequest): Response<AudiovisualResponse>

    @PUT(Constants.AUDIOVISUAL_URL)
    suspend fun updateAudiovisual(@Body request: AudiovisualRequest): Response<AudiovisualResponse>

    @GET(Constants.AUDIOVISUAL_URL)
    suspend fun getAllAudiovisuals(): Response<List<AudiovisualResponse>>

    @DELETE(Constants.AUDIOVISUAL_URL + Constants.ID)
    suspend fun deleteAudiovisual(@Path("id") id: Long): Response<Void>

    @GET(Constants.TITLE_URL)
    suspend fun getAllTitles(): Response<List<TitleResponse>>

    @GET(Constants.TITLE_URL + Constants.FILTER)
    suspend fun getAllTitlesByFilter(@Path("filter") filter: String): Response<List<TitleResponse>>

    @GET(Constants.PLATFORM_URL)
    suspend fun getAllPlatforms(): Response<List<PlatformResponse>>

}