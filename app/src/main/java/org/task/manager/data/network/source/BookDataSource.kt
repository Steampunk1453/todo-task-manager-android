package org.task.manager.data.network.source

import org.task.manager.data.network.model.request.BookRequest
import org.task.manager.data.network.model.response.BookResponse
import org.task.manager.data.network.model.response.BookshopResponse
import org.task.manager.data.network.model.response.EditorialResponse
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import retrofit2.Response
import java.io.IOException

class BookDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun create(bookRequest: BookRequest): BookResponse {
        val bookApi = dataSourceProvider.getBookDataSource()
        val response = bookApi.createBook(bookRequest)

        return checkAudiovisualResponse(response)
    }

    suspend fun update(bookRequest: BookRequest): BookResponse {
        val bookApi = dataSourceProvider.getBookDataSource()
        val response = bookApi.updateBook(bookRequest)

        return checkAudiovisualResponse(response)
    }

    suspend fun getAll(): List<BookResponse>{
        val bookApi = dataSourceProvider.getBookDataSource()
        val response = bookApi.getAllBooks()

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

    suspend fun get(id: Long): BookResponse {
        val bookApi = dataSourceProvider.getBookDataSource()
        val response = bookApi.getBook(id)

        return checkAudiovisualResponse(response)
    }

    suspend fun delete(id: Long) {
        val bookApi = dataSourceProvider.getBookDataSource()
        bookApi.deleteBook(id)
    }

    suspend fun getAllBookshops(): List<BookshopResponse>{
        val bookApi = dataSourceProvider.getBookDataSource()
        val response = bookApi.getAllBookshops()

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

    suspend fun getAllEditorials(): List<EditorialResponse>{
        val bookApi = dataSourceProvider.getBookDataSource()
        val response = bookApi.getAllEditorials()

        if (!response.isSuccessful) throw IOException(response.message())

        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

    private fun checkAudiovisualResponse(response: Response<BookResponse>): BookResponse {
        if (!response.isSuccessful) throw IOException(response.message())
        return response.body() ?: throw IllegalStateException(ILLEGAL_STATE_EXCEPTION_CAUSE)
    }

}