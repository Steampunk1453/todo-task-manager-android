package org.task.manager.data.network.source

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.api.BookApi
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.data.network.model.response.toResponse
import org.task.manager.stub.BookStub
import org.task.manager.stub.BookshopResponseStub
import org.task.manager.stub.EditorialResponseStub
import retrofit2.Response
import retrofit2.Response.success
import java.io.IOException
import kotlin.random.Random
import kotlin.test.assertFailsWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class BookRemoteDataSourceTest {

    @MockK
    private lateinit var provider: DataSourceProvider

    @MockK
    private lateinit var bookApi: BookApi

    @InjectMockKs
    private lateinit var bookRemoteDataSource: BookRemoteDataSource

    @Test
    fun `should return successful book response when create`() =
        runBlockingTest {
            // Given
            val bookRequest = BookStub.random().toRequest()
            val bookResponse = BookStub.random().toResponse()
            coEvery { provider.getBookDataSource() } returns bookApi
            coEvery { bookApi.createBook(any()) } returns success(bookResponse)
            // When
            val response = bookRemoteDataSource.create(bookRequest)
            // Then
            response shouldNotBe {null}
            response shouldBe bookResponse
            response.id shouldBe bookResponse.id
            response.title shouldBe bookResponse.title
            response.author shouldBe bookResponse.author
            response.genre shouldBe bookResponse.genre
            response.editorial shouldBe bookResponse.editorial
            response.editorialUrl shouldBe bookResponse.editorialUrl
            response.bookshop shouldBe bookResponse.bookshop
            response.bookshopUrl shouldBe bookResponse.bookshopUrl
            response.startDate shouldBe bookResponse.startDate
            response.deadline shouldBe bookResponse.deadline
            response.check shouldBe bookResponse.check
            response.userResponse?.id shouldBe bookResponse.userResponse?.id
        }

    @Test
    fun `should return successful book response when update`() {
        runBlockingTest {
            // Given
            val bookRequest = BookStub.random().toRequest()
            val bookResponse = BookStub.random().toResponse()
            coEvery { provider.getBookDataSource() } returns bookApi
            coEvery { bookApi.updateBook(any()) } returns success(bookResponse)
            // When
            val response = bookRemoteDataSource.update(bookRequest)
            // Then
            response shouldNotBe {null}
            response shouldBe bookResponse
            response.id shouldBe bookResponse.id
            response.title shouldBe bookResponse.title
            response.author shouldBe bookResponse.author
            response.genre shouldBe bookResponse.genre
            response.editorial shouldBe bookResponse.editorial
            response.editorialUrl shouldBe bookResponse.editorialUrl
            response.bookshop shouldBe bookResponse.bookshop
            response.bookshopUrl shouldBe bookResponse.bookshopUrl
            response.startDate shouldBe bookResponse.startDate
            response.deadline shouldBe bookResponse.deadline
            response.check shouldBe bookResponse.check
            response.userResponse?.id shouldBe bookResponse.userResponse?.id
        }
    }

    @Test
    fun `should return successful response with books when get all`() {
        runBlockingTest {
            // Given
            val bookResponse = BookStub.random().toResponse()
            val bookResponse1 = BookStub.random().toResponse()
            val books = listOf(bookResponse, bookResponse1)
            coEvery { provider.getBookDataSource() } returns bookApi
            coEvery { bookApi.getAllBooks() } returns success(books)
            // When
            val response = bookRemoteDataSource.findAll()
            // Then
            response shouldNotBe {null}
            response[0] shouldBe bookResponse
            response[0].id shouldBe bookResponse.id
            response[0].title shouldBe bookResponse.title
            response[0].author shouldBe bookResponse.author
            response[0].genre shouldBe bookResponse.genre
            response[0].editorial shouldBe bookResponse.editorial
            response[0].editorialUrl shouldBe bookResponse.editorialUrl
            response[0].bookshop shouldBe bookResponse.bookshop
            response[0].bookshopUrl shouldBe bookResponse.bookshopUrl
            response[0].startDate shouldBe bookResponse.startDate
            response[0].deadline shouldBe bookResponse.deadline
            response[0].check shouldBe bookResponse.check
            response[0].userResponse?.id shouldBe bookResponse.userResponse?.id
            response[1] shouldBe bookResponse1
            response[1].title shouldBe bookResponse1.title
        }
    }

    @Test
    fun `should return successful response with book when found by id`() {
        runBlockingTest {
            // Given
            val bookResponse = BookStub.random().toResponse()
            val id = bookResponse.id
            coEvery { provider.getBookDataSource() } returns bookApi
            coEvery { bookApi.getBook(any()) } returns success(bookResponse)
            // When
            val response = bookRemoteDataSource.findById(id)
            // Then
            response shouldNotBe {null}
            response shouldNotBe {null}
            response shouldBe bookResponse
            response.id shouldBe bookResponse.id
            response.title shouldBe bookResponse.title
            response.author shouldBe bookResponse.author
            response.genre shouldBe bookResponse.genre
            response.editorial shouldBe bookResponse.editorial
            response.editorialUrl shouldBe bookResponse.editorialUrl
            response.bookshop shouldBe bookResponse.bookshop
            response.bookshopUrl shouldBe bookResponse.bookshopUrl
            response.startDate shouldBe bookResponse.startDate
            response.deadline shouldBe bookResponse.deadline
            response.check shouldBe bookResponse.check
            response.userResponse?.id shouldBe bookResponse.userResponse?.id
        }
    }

    @Test
    fun `should delete a book found by id`() {
        runBlockingTest {
            // Given
            val id = Random.nextLong(1, 5000)
            coEvery { provider.getBookDataSource() } returns bookApi
            coEvery { bookApi.deleteBook(any()) } returns success(null)
            bookRemoteDataSource.delete(id)
            // Then
            coVerify(atLeast = 1) {  bookApi.deleteBook(id) }
        }

    }

    @Test
    fun `should return successful response with editorials when get all`() {
        runBlockingTest {
            // Given
            val editorialResponse = EditorialResponseStub.random()
            val editorialResponse1 = EditorialResponseStub.random()
            val editorials = listOf(editorialResponse, editorialResponse1)
            coEvery { provider.getBookDataSource() } returns bookApi
            coEvery { bookApi.getAllEditorials() } returns success(editorials)
            // When
            val response = bookRemoteDataSource.getAllEditorials()
            // Then
            response shouldNotBe {null}
            response[0] shouldBe editorialResponse
            response[0].id shouldBe editorialResponse.id
            response[0].name shouldBe editorialResponse.name
            response[1] shouldBe editorialResponse1
            response[1].id shouldBe editorialResponse1.id
            response[1].name shouldBe editorialResponse1.name
        }
    }

    @Test
    fun `should return successful response with bookshops when get all`() {
        runBlockingTest {
            // Given
            val bookshopResponse = BookshopResponseStub.random()
            val bookshopResponse1 = BookshopResponseStub.random()
            val books = listOf(bookshopResponse, bookshopResponse1)
            coEvery { provider.getBookDataSource() } returns bookApi
            coEvery { bookApi.getAllBookshops() } returns success(books)
            // When
            val response = bookRemoteDataSource.getAllBookshops()
            // Then
            response shouldNotBe {null}
            response[0] shouldBe bookshopResponse
            response[0].id shouldBe bookshopResponse.id
            response[0].name shouldBe bookshopResponse.name
            response[0].url shouldBe bookshopResponse.url
            response[1] shouldBe bookshopResponse1
            response[1].id shouldBe bookshopResponse1.id
            response[1].name shouldBe bookshopResponse1.name
            response[1].url shouldBe bookshopResponse1.url
        }
    }

    @Test
    fun `should return empty body in response and throws exception when when create book`() = runBlockingTest {
        // Given
        val bookRequest = BookStub.random().toRequest()
        every { provider.getBookDataSource() } returns bookApi
        coEvery { bookApi.createBook(any()) } returns success(null)
        // When & Then
        assertFailsWith<IllegalStateException>("Empty response body") {
            bookRemoteDataSource.create(bookRequest)
        }
    }

    @Test
    fun `should return unsuccessful response throws exception when when create book`() = runBlockingTest {
        // Given
        val bookRequest = BookStub.random().toRequest()
        every { provider.getBookDataSource() } returns bookApi
        coEvery { bookApi.createBook(any()) } returns Response.error(
            400,
            ResponseBody.create(null, ByteArray(0))
        )
        // When & Then
        assertFailsWith<IOException>("Unsuccessful response") {
            bookRemoteDataSource.create(bookRequest)
        }
    }

}
