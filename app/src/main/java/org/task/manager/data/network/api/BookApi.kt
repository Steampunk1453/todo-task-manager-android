package org.task.manager.data.network.api

import org.task.manager.data.network.model.request.BookRequest
import org.task.manager.data.network.model.response.BookResponse
import org.task.manager.data.network.model.response.EditorialResponse
import org.task.manager.shared.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BookApi {

    @POST(Constants.BOOK_URL)
    suspend fun createBook(@Body request: BookRequest): Response<BookResponse>

    @PUT(Constants.BOOK_URL)
    suspend fun updateBook(@Body request: BookRequest): Response<BookResponse>

    @GET(Constants.BOOK_URL)
    suspend fun getAllBooks(): Response<List<BookResponse>>

    @GET(Constants.BOOK_URL + Constants.ID)
    suspend fun getBook(@Path("id") id: Long): Response<BookResponse>

    @DELETE(Constants.BOOK_URL + Constants.ID)
    suspend fun deleteBook(@Path("id") id: Long): Response<Void>

    @GET(Constants.BOOKSHOP_URL)
    suspend fun getAllBookshops(): Response<List<BookResponse>>

    @GET(Constants.EDITORIAL_URL)
    suspend fun getAllEditorials(): Response<List<EditorialResponse>>

}