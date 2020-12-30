package org.task.manager.data.repository

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.local.model.toEntity
import org.task.manager.data.local.source.GenreLocalDataSource
import org.task.manager.data.network.model.response.toResponse
import org.task.manager.data.network.source.GenreRemoteDataSource
import org.task.manager.domain.Result
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import org.task.manager.stub.GenreStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GenreRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var remoteDataSource: GenreRemoteDataSource

    @MockK(relaxed = true)
    private lateinit var localDataSource: GenreLocalDataSource

    @InjectMockKs
    private lateinit var repository: DefaultGenreRepository

    @BeforeEach
    fun setup() {
        repository = DefaultGenreRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `should return successful result when get all genres`() = runBlockingTest {
        // Given
        val genre = GenreStub.random().toEntity()
        val genres = listOf(genre)
        coEvery { localDataSource.findAll() } returns genres
        // When
        val result = repository.getAll()
        // Then
        coVerify { localDataSource.findAll() }
        result shouldNotBe null
        result as Result.Success
        result.data[0].id shouldBe genre.id
        result.data[0].name shouldBe genre.name
        result.data[0].isLiterary shouldBe genre.isLiterary
    }

    @Test
    fun `should return successful result when get all bookshops with empty local data`() =
            runBlockingTest {
                // Given
                val genre = GenreStub.random().toResponse()
                val genres = listOf(genre)
                coEvery { localDataSource.findAll() } returns listOf()
                coEvery { remoteDataSource.getAll() } returns genres
                // When
                val result = repository.getAll()
                // Then
                coVerify { localDataSource.findAll() }
                coVerify { remoteDataSource.getAll() }
                coVerify { localDataSource.saveAll(any()) }
                result shouldNotBe null
                result as Result.Success
                result.data[0].id shouldBe genre.id
                result.data[0].name shouldBe genre.name
            }
    @Test
    fun `should return result with error exception when get all bookshops`() = runBlockingTest {
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

}