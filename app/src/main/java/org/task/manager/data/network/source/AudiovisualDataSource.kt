package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.model.response.AudiovisualResponse
import org.task.manager.data.network.model.response.PlatformResponse
import org.task.manager.data.network.model.response.TitleResponse
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import retrofit2.Response
import java.io.IOException

class AudiovisualDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun create(audiovisualRequest: AudiovisualRequest): AudiovisualResponse {
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.createAudiovisual(audiovisualRequest)

        return checkAudiovisualResponse(response)
    }

    suspend fun update(audiovisualRequest: AudiovisualRequest): AudiovisualResponse {
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.updateAudiovisual(audiovisualRequest)

        return checkAudiovisualResponse(response)
    }

    suspend fun getAll(): List<AudiovisualResponse>{
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.getAudiovisuals()

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

    suspend fun get(id: Long): AudiovisualResponse {
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.getAudiovisual(id)

        return checkAudiovisualResponse(response)
    }

    suspend fun delete(id: Long) {
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        audiovisualApi.deleteAudiovisual(id)
    }

    suspend fun getAllTitles(): List<TitleResponse>{
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.getTitles()

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

    suspend fun getAllPlatforms(): List<PlatformResponse>{
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.getPlatforms()

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

    private fun checkAudiovisualResponse(response: Response<AudiovisualResponse>): AudiovisualResponse {
        if (!response.isSuccessful) throw IOException(response.message())
        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

}