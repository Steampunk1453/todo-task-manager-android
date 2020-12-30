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
import org.task.manager.data.local.source.AudiovisualLocalDataSource
import org.task.manager.data.network.model.response.toResponse
import org.task.manager.data.network.source.AudiovisualRemoteDataSource
import org.task.manager.domain.Result
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import org.task.manager.stub.AudiovisualStub
import org.task.manager.stub.PlatformStub
import org.task.manager.stub.TitleStub

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
    fun `should return successful result when update audiovisual`() = runBlockingTest {
        // Given
        val audiovisual = AudiovisualStub.random()
        val audiovisualResponse = audiovisual.toResponse()
        coEvery { remoteDataSource.update(any()) } returns audiovisualResponse
        coEvery { localDataSource.update(any()) } just Runs
        // When
        val result = repository.update(audiovisual)
        // Then
        coVerify { localDataSource.update(any()) }
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
    fun `should return result with error exception when update audiovisual`() = runBlockingTest {
        // Given
        val audiovisual = AudiovisualStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { remoteDataSource.update(any()) } throws error
        coEvery { localDataSource.update(any()) } just Runs
        // When
        val result = repository.update(audiovisual)
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when get all audiovisuals`() = runBlockingTest {
        // Given
        val audiovisual = AudiovisualStub.random().toEntity()
        val audiovisuals = listOf(audiovisual)
        coEvery { localDataSource.findAll() } returns audiovisuals
        // When
        val result = repository.getAll()
        // Then
        coVerify { localDataSource.findAll() }
        result shouldNotBe null
        result as Result.Success
        result.data[0].id shouldBe audiovisual.id
        result.data[0].title shouldBe audiovisual.title
        result.data[0].genre shouldBe audiovisual.genre
        result.data[0].platform shouldBe audiovisual.platform
        result.data[0].platformUrl shouldBe audiovisual.platformUrl
        result.data[0].startDate shouldBe audiovisual.startDate
        result.data[0].deadline shouldBe audiovisual.deadline
        result.data[0].check shouldBe audiovisual.check
        result.data[0].user?.id shouldBe audiovisual.userId
    }

    @Test
    fun `should return successful result when get all audiovisuals with empty local data`() =
            runBlockingTest {
                // Given
                val audiovisual = AudiovisualStub.random().toResponse()
                val audiovisuals = listOf(audiovisual)
                coEvery { localDataSource.findAll() } returns listOf()
                coEvery { remoteDataSource.findAll() } returns audiovisuals
                // When
                val result = repository.getAll()
                // Then
                coVerify { localDataSource.findAll() }
                coVerify { remoteDataSource.findAll() }
                coVerify { localDataSource.saveAll(any()) }
                result shouldNotBe null
                result as Result.Success
                result.data[0].id shouldBe audiovisual.id
                result.data[0].title shouldBe audiovisual.title
                result.data[0].genre shouldBe audiovisual.genre
                result.data[0].platform shouldBe audiovisual.platform
                result.data[0].platformUrl shouldBe audiovisual.platformUrl
                result.data[0].startDate shouldBe audiovisual.startDate
                result.data[0].deadline shouldBe audiovisual.deadline
                result.data[0].check shouldBe audiovisual.check
                result.data[0].user?.id shouldBe audiovisual.userResponse?.id
            }
    @Test
    fun `should return result with error exception when get all audiovisuals`() = runBlockingTest {
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
    fun `should return successful result when get all titles`() = runBlockingTest {
        // Given
        val title = TitleStub.random().toEntity()
        val titles = listOf(title)
        coEvery { localDataSource.findAllTitles() } returns titles
        // When
        val result = repository.getAllTitles()
        // Then
        coVerify { localDataSource.findAllTitles() }
        result shouldNotBe null
        result as Result.Success
        result.data[0].id shouldBe title.id
        result.data[0].name shouldBe title.name
    }

    @Test
    fun `should return successful result when get all titles with empty local data`() =
            runBlockingTest {
                // Given
                val title = TitleStub.random().toResponse()
                val titles = listOf(title)
                coEvery { localDataSource.findAllTitles() } returns listOf()
                coEvery { remoteDataSource.getAllTitles() } returns titles
                // When
                val result = repository.getAllTitles()
                // Then
                coVerify { localDataSource.findAllTitles() }
                coVerify { remoteDataSource.getAllTitles() }
                coVerify { localDataSource.saveAllTitles(any()) }
                result shouldNotBe null
                result as Result.Success
                result.data[0].id shouldBe title.id
                result.data[0].name shouldBe title.name
            }
    @Test
    fun `should return result with error exception when get all titles`() = runBlockingTest {
        // Given
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { localDataSource.findAllTitles() } throws error
        // When
        val result = repository.getAllTitles()
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when get all platforms`() = runBlockingTest {
        // Given
        val platform = PlatformStub.random().toEntity()
        val platforms = listOf(platform)
        coEvery { localDataSource.findAllPlatforms() } returns platforms
        // When
        val result = repository.getAllPlatforms()
        // Then
        coVerify { localDataSource.findAllPlatforms() }
        result shouldNotBe null
        result as Result.Success
        result.data[0].id shouldBe platform.id
        result.data[0].name shouldBe platform.name
    }

    @Test
    fun `should return successful result when get all platforms with empty local data`() =
            runBlockingTest {
                // Given
                val platform = PlatformStub.random().toResponse()
                val platforms = listOf(platform)
                coEvery { localDataSource.findAllPlatforms() } returns listOf()
                coEvery { remoteDataSource.getAllPlatforms() } returns platforms
                // When
                val result = repository.getAllPlatforms()
                // Then
                coVerify { localDataSource.findAllPlatforms() }
                coVerify { remoteDataSource.getAllPlatforms() }
                coVerify { localDataSource.saveAllPlatforms(any()) }
                result shouldNotBe null
                result as Result.Success
                result.data[0].id shouldBe platform.id
                result.data[0].name shouldBe platform.name
            }
    @Test
    fun `should return result with error exception when get all platforms`() = runBlockingTest {
        // Given
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { localDataSource.findAllPlatforms() } throws error
        // When
        val result = repository.getAllPlatforms()
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

}