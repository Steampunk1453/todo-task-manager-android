package org.task.manager.data.network.source

import org.task.manager.data.network.model.response.GenreResponse
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import java.io.IOException

class GenreDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun getAll(): List<GenreResponse>{
        val genreApi = dataSourceProvider.getGenreDataSource()
        val response = genreApi.getGenres()

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

}
