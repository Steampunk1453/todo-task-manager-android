package org.task.manager.data.network.api

import org.task.manager.data.network.model.response.GenreResponse
import org.task.manager.shared.Constants
import retrofit2.Response
import retrofit2.http.GET

interface GenreApi {
    @GET(Constants.GENRE_URL)
    suspend fun getGenres(): Response<List<GenreResponse>>
}