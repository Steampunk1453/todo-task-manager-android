package org.task.manager.data.network.api

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.model.response.AudiovisualResponse
import org.task.manager.data.network.model.response.PlatformResponse
import org.task.manager.data.network.model.response.TitleResponse
import org.task.manager.shared.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AudiovisualApi {

    @POST(Constants.AUDIOVISUAL_URL)
    suspend fun createAudiovisual(@Body request: AudiovisualRequest): Response<AudiovisualResponse>

    @PUT(Constants.AUDIOVISUAL_URL)
    suspend fun updateAudiovisual(@Body request: AudiovisualRequest): Response<AudiovisualResponse>

    @GET(Constants.AUDIOVISUAL_URL)
    suspend fun getAudiovisuals(): Response<List<AudiovisualResponse>>

    @GET(Constants.AUDIOVISUAL_URL + Constants.AUDIOVISUAL_ID)
    suspend fun getAudiovisual(@Path("id") id: Long): Response<AudiovisualResponse>

    @DELETE(Constants.AUDIOVISUAL_URL + Constants.AUDIOVISUAL_ID)
    suspend fun deleteAudiovisual(@Path("id") id: Long): Response<Void>

    @GET(Constants.TITLE_URL)
    suspend fun getTitles(): Response<List<TitleResponse>>

    @GET(Constants.PLATFORM_URL)
    suspend fun getPlatforms(): Response<List<PlatformResponse>>

}