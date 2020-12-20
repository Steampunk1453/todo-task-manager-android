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
import org.task.manager.data.local.source.AudiovisualLocalDataSource
import org.task.manager.data.network.model.response.toResponse
import org.task.manager.data.network.source.AudiovisualRemoteDataSource
import org.task.manager.domain.Result
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import org.task.manager.stub.AudiovisualStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class AudiovisualRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var remoteDataSource: AudiovisualRemoteDataSource

    @MockK(relaxed = true)
    private lateinit var localDataSource: AudiovisualLocalDataSource

    @InjectMockKs
    private lateinit var repository: DefaultAudiovisualRepository

    @BeforeEach
    fun setup() {
        repository = DefaultAudiovisualRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `should return successful result when save audiovisual`() = runBlockingTest {
        // Given
        val audiovisual = AudiovisualStub.random()
        val audiovisualResponse = audiovisual.toResponse()
        coEvery { remoteDataSource.create(any()) } returns audiovisualResponse
        coEvery { localDataSource.save(any()) } just Runs
        // When
        val result = repository.save(audiovisual)
        // Then
        coVerify { localDataSource.save(any()) }
        result shouldNotBe null
        result as Result.Success
        result.data.id shouldBe audiovisual.id
        result.data.title shouldBe audiovisual.title
        result.data.genre shouldBe audiovisual.genre
        result.data.platform shouldBe audiovisual.platform
        result.data.platformUrl shouldBe audiovisual.platformUrl
        result.data.startDate shouldBe audiovisual.startDate
        result.data.deadline shouldBe audiovisual.deadline
        result.data.check shouldBe audiovisual.check
        result.data.user?.id shouldBe audiovisual.user?.id
    }

    @Test
    fun `should return result with error exception when save audiovisual`() = runBlockingTest {
        // Given
        val audiovisual = AudiovisualStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { remoteDataSource.create(any()) } throws error
        coEvery { localDataSource.save(any()) } just Runs
        // When
        val result = repository.save(audiovisual)
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun getAll() {
    }

    @Test
    fun remove() {
    }

    @Test
    fun getAllTitles() {
    }

    @Test
    fun getAllPlatforms() {
    }
}