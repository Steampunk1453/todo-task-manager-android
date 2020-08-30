package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.AudiovisualRequest
import org.task.manager.data.network.model.response.AudiovisualResponse
import java.io.IOException

class AudiovisualDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun getAll(): List<AudiovisualResponse>{
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.getAudiovisuals()

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException("Empty response body")
    }

    suspend fun create(audiovisualRequest: AudiovisualRequest): AudiovisualResponse {
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.createAudiovisual(audiovisualRequest)

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException("Empty response body")
    }

    suspend fun delete(id: Long) {
        val audiovisualApi = dataSourceProvider.getAudiovisualDataSource()
        val response = audiovisualApi.deleteAudiovisual(id)

        if (!response.isSuccessful) throw IOException(response.message())
    }

}