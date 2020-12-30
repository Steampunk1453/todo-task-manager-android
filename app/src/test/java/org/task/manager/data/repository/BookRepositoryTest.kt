package org.task.manager.data.repository

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.local.model.toEntity
import org.task.manager.data.local.source.BookLocalDataSource
import org.task.manager.data.network.model.response.toResponse
import org.task.manager.data.network.source.BookRemoteDataSource
import org.task.manager.domain.Result
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import org.task.manager.stub.BookStub
import org.task.manager.stub.BookshopStub
import org.task.manager.stub.EditorialStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class BookRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var remoteDataSource: BookRemoteDataSource

    @MockK(relaxed = true)
    private lateinit var localDataSource: BookLocalDataSource

    @InjectMockKs
    private lateinit var repository: DefaultBookRepository

    @BeforeEach
    fun setup() {
        repository = DefaultBookRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `should return successful result when save book`() = runBlockingTest {
        // Given
        val book = BookStub.random()
        val bookResponse = book.toResponse()
        coEvery { remoteDataSource.create(any()) } returns bookResponse
        coEvery { localDataSource.save(any()) } just Runs
        // When
        val result = repository.save(book)
        // Then
        coVerify { localDataSource.save(any()) }
        result shouldNotBe null
        result as Result.Success
        result.data.id shouldBe book.id
        result.data.title shouldBe book.title
        result.data.genre shouldBe book.genre
        result.data.editorial shouldBe book.editorial
        result.data.editorialUrl shouldBe book.editorialUrl
        result.data.bookshop shouldBe book.bookshop
        result.data.bookshopUrl shouldBe book.bookshopUrl
        result.data.startDate shouldBe book.startDate
        result.data.deadline shouldBe book.deadline
        result.data.check shouldBe book.check
        result.data.user?.id shouldBe book.user?.id
    }

    @Test
    fun `should return result with error exception when save book`() = runBlockingTest {
        // Given
        val book = BookStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { remoteDataSource.create(any()) } throws error
        coEvery { localDataSource.save(any()) } just Runs
        // When
        val result = repository.save(book)
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when update book`() = runBlockingTest {
        // Given
        val book = BookStub.random()
        val bookResponse = book.toResponse()
        coEvery { remoteDataSource.update(any()) } returns bookResponse
        coEvery { localDataSource.update(any()) } just Runs
        // When
        val result = repository.update(book)
        // Then
        coVerify { localDataSource.update(any()) }
        result shouldNotBe null
        result as Result.Success
        result.data.id shouldBe book.id
        result.data.title shouldBe book.title
        result.data.genre shouldBe book.genre
        result.data.editorial shouldBe book.editorial
        result.data.editorialUrl shouldBe book.editorialUrl
        result.data.bookshop shouldBe book.bookshop
        result.data.bookshopUrl shouldBe book.bookshopUrl
        result.data.startDate shouldBe book.startDate
        result.data.deadline shouldBe book.deadline
        result.data.check shouldBe book.check
        result.data.user?.id shouldBe book.user?.id
    }

    @Test
    fun `should return result with error exception when update book`() = runBlockingTest {
        // Given
        val book = BookStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { remoteDataSource.update(any()) } throws error
        coEvery { localDataSource.update(any()) } just Runs
        // When
        val result = repository.update(book)
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when get all books`() = runBlockingTest {
        // Given
        val book = BookStub.random().toEntity()
        val books = listOf(book)
        coEvery { localDataSource.findAll() } returns books
        // When
        val result = repository.getAll()
        // Then
        coVerify { localDataSource.findAll() }
        result shouldNotBe null
        result as Result.Success
        result.data[0].id shouldBe book.id
        result.data[0].title shouldBe book.title
        result.data[0].genre shouldBe book.genre
        result.data[0].editorial shouldBe book.editorial
        result.data[0].editorialUrl shouldBe book.editorialUrl
        result.data[0].bookshop shouldBe book.bookshop
        result.data[0].bookshopUrl shouldBe book.bookshopUrl
        result.data[0].startDate shouldBe book.startDate
        result.data[0].deadline shouldBe book.deadline
        result.data[0].check shouldBe book.check
        result.data[0].user?.id shouldBe book.userId
    }

    @Test
    fun `should return successful result when get all books with empty local data`() =
            runBlockingTest {
                // Given
                val book = BookStub.random().toResponse()
                val books = listOf(book)
                coEvery { localDataSource.findAll() } returns listOf()
                coEvery { remoteDataSource.findAll() } returns books
                // When
                val result = repository.getAll()
                // Then
                coVerify { localDataSource.findAll() }
                coVerify { remoteDataSource.findAll() }
                coVerify { localDataSource.saveAll(any()) }
                result shouldNotBe null
                result as Result.Success
                result.data[0].id shouldBe book.id
                result.data[0].title shouldBe book.title
                result.data[0].genre shouldBe book.genre
                result.data[0].editorial shouldBe book.editorial
                result.data[0].editorialUrl shouldBe book.editorialUrl
                result.data[0].bookshop shouldBe book.bookshop
                result.data[0].bookshopUrl shouldBe book.bookshopUrl
                result.data[0].startDate shouldBe book.startDate
                result.data[0].deadline shouldBe book.deadline
                result.data[0].check shouldBe book.check
                result.data[0].user?.id shouldBe book.userResponse?.id
            }
    @Test
    fun `should return result with error exception when get all books`() = runBlockingTest {
        // Given
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { localDataSource.findAll() } throws error
        // When
        val result = repository.getAll()
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when get all editorials`() = runBlockingTest {
        // Given
        val editorial = EditorialStub.random().toEntity()
        val editorials = listOf(editorial)
        coEvery { localDataSource.findAllEditorials() } returns editorials
        // When
        val result = repository.getAllEditorials()
        // Then
        coVerify { localDataSource.findAllEditorials() }
        result shouldNotBe null
        result as Result.Success
        result.data[0].id shouldBe editorial.id
        result.data[0].name shouldBe editorial.name
        result.data[0].url shouldBe editorial.url
    }

    @Test
    fun `should return successful result when get all editorials with empty local data`() =
            runBlockingTest {
                // Given
                val editorial = EditorialStub.random().toResponse()
                val editorials = listOf(editorial)
                coEvery { localDataSource.findAllEditorials() } returns listOf()
                coEvery { remoteDataSource.findAllEditorials() } returns editorials
                // When
                val result = repository.getAllEditorials()
                // Then
                coVerify { localDataSource.findAllEditorials() }
                coVerify { remoteDataSource.findAllEditorials() }
                coVerify { localDataSource.saveAllEditorials(any()) }
                result shouldNotBe null
                result as Result.Success
                result.data[0].id shouldBe editorial.id
                result.data[0].name shouldBe editorial.name
            }
    @Test
    fun `should return result with error exception when get all editorials`() = runBlockingTest {
        // Given
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { localDataSource.findAllEditorials() } throws error
        // When
        val result = repository.getAllEditorials()
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when get all bookshops`() = runBlockingTest {
        // Given
        val bookshop = BookshopStub.random().toEntity()
        val bookshops = listOf(bookshop)
        coEvery { localDataSource.findAllBookshops() } returns bookshops
        // When
        val result = repository.getAllBookshops()
        // Then
        coVerify { localDataSource.findAllBookshops() }
        result shouldNotBe null
        result as Result.Success
        result.data[0].id shouldBe bookshop.id
        result.data[0].name shouldBe bookshop.name
    }

    @Test
    fun `should return successful result when get all bookshops with empty local data`() =
            runBlockingTest {
                // Given
                val bookshop = BookshopStub.random().toResponse()
                val bookshops = listOf(bookshop)
                coEvery { localDataSource.findAllBookshops() } returns listOf()
                coEvery { remoteDataSource.findAllBookshops() } returns bookshops
                // When
                val result = repository.getAllBookshops()
                // Then
                coVerify { localDataSource.findAllBookshops() }
                coVerify { remoteDataSource.findAllBookshops() }
                coVerify { localDataSource.saveAllBookshops(any()) }
                result shouldNotBe null
                result as Result.Success
                result.data[0].id shouldBe bookshop.id
                result.data[0].name shouldBe bookshop.name
            }
    @Test
    fun `should return result with error exception when get all bookshops`() = runBlockingTest {
        // Given
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { localDataSource.findAllBookshops() } throws error
        // When
        val result = repository.getAllBookshops()
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

}