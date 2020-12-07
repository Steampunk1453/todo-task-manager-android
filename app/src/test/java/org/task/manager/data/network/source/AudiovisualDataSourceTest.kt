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
import org.task.manager.data.network.api.AudiovisualApi
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.data.network.model.response.toResponse
import org.task.manager.stub.AudiovisualStub
import org.task.manager.stub.PlatformResponseStub
import org.task.manager.stub.TitleResponseStub
import retrofit2.Response
import retrofit2.Response.success
import java.io.IOException
import kotlin.random.Random
import kotlin.test.assertFailsWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class AudiovisualDataSourceTest {

    @MockK
    private lateinit var provider: DataSourceProvider

    @MockK
    private lateinit var audiovisualApi: AudiovisualApi

    @InjectMockKs
    private lateinit var audiovisualDataSource: AudiovisualRemoteDataSource

    @Test
    fun `should return successful audiovisual response when create`() =
        runBlockingTest {
            // Given
            val audiovisualRequest = AudiovisualStub.random().toRequest()
            val audiovisualResponse = AudiovisualStub.random().toResponse()
            coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
            coEvery { audiovisualApi.createAudiovisual(any()) } returns success(audiovisualResponse)
            // When
            val response = audiovisualDataSource.create(audiovisualRequest)
            // Then
            response shouldNotBe {null}
            response shouldBe audiovisualResponse
            response.id shouldBe audiovisualResponse.id
            response.title shouldBe audiovisualResponse.title
            response.genre shouldBe audiovisualResponse.genre
            response.platform shouldBe audiovisualResponse.platform
            response.platformUrl shouldBe audiovisualResponse.platformUrl
            response.startDate shouldBe audiovisualResponse.startDate
            response.deadline shouldBe audiovisualResponse.deadline
            response.check shouldBe audiovisualResponse.check
            response.userResponse?.id shouldBe audiovisualResponse.userResponse?.id
        }

    @Test
    fun `should return successful audiovisual response when update`() {
        runBlockingTest {
            // Given
            val audiovisualRequest = AudiovisualStub.random().toRequest()
            val audiovisualResponse = AudiovisualStub.random().toResponse()
            coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
            coEvery { audiovisualApi.updateAudiovisual(any()) } returns success(audiovisualResponse)
            // When
            val response = audiovisualDataSource.update(audiovisualRequest)
            // Then
            response shouldNotBe {null}
            response shouldBe audiovisualResponse
            response.id shouldBe audiovisualResponse.id
            response.title shouldBe audiovisualResponse.title
            response.genre shouldBe audiovisualResponse.genre
            response.platform shouldBe audiovisualResponse.platform
            response.platformUrl shouldBe audiovisualResponse.platformUrl
            response.startDate shouldBe audiovisualResponse.startDate
            response.deadline shouldBe audiovisualResponse.deadline
            response.check shouldBe audiovisualResponse.check
            response.userResponse?.id shouldBe audiovisualResponse.userResponse?.id
        }
    }

    @Test
    fun `should return successful response with audiovisuals when get all`() {
        runBlockingTest {
            // Given
            val audiovisualResponse = AudiovisualStub.random().toResponse()
            val audiovisualResponse1 = AudiovisualStub.random().toResponse()
            val audiovisuals = listOf(audiovisualResponse, audiovisualResponse1)
            coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
            coEvery { audiovisualApi.getAllAudiovisuals() } returns success(audiovisuals)
            // When
            val response = audiovisualDataSource.findAll()
            // Then
            response shouldNotBe {null}
            response[0] shouldBe audiovisualResponse
            response[0].id shouldBe audiovisualResponse.id
            response[0].title shouldBe audiovisualResponse.title
            response[0].genre shouldBe audiovisualResponse.genre
            response[0].platform shouldBe audiovisualResponse.platform
            response[0].platformUrl shouldBe audiovisualResponse.platformUrl
            response[0].startDate shouldBe audiovisualResponse.startDate
            response[0].deadline shouldBe audiovisualResponse.deadline
            response[0].check shouldBe audiovisualResponse.check
            response[0].userResponse?.id shouldBe audiovisualResponse.userResponse?.id
            response[1] shouldBe audiovisualResponse1
            response[1].title shouldBe audiovisualResponse1.title
        }
    }

    @Test
    fun `should return successful response with audiovisual when found by id`() {
        runBlockingTest {
            // Given
            val audiovisualResponse = AudiovisualStub.random().toResponse()
            val id = audiovisualResponse.id
            coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
            coEvery { audiovisualApi.getAudiovisual(any()) } returns success(audiovisualResponse)
            // When
            val response = audiovisualDataSource.findById(id)
            // Then
            response shouldNotBe {null}
            response shouldBe audiovisualResponse
            response.id shouldBe audiovisualResponse.id
            response.title shouldBe audiovisualResponse.title
            response.genre shouldBe audiovisualResponse.genre
            response.platform shouldBe audiovisualResponse.platform
            response.platformUrl shouldBe audiovisualResponse.platformUrl
            response.startDate shouldBe audiovisualResponse.startDate
            response.deadline shouldBe audiovisualResponse.deadline
            response.check shouldBe audiovisualResponse.check
            response.userResponse?.id shouldBe audiovisualResponse.userResponse?.id
        }
    }

    @Test
    fun `should delete a audiovisual found by id`() {
        runBlockingTest {
            // Given
            val id = Random.nextLong(1, 5000)
            coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
            coEvery { audiovisualApi.deleteAudiovisual(any()) } returns success(null)
            audiovisualDataSource.delete(id)
            // Then
            coVerify(atLeast = 1) {  audiovisualApi.deleteAudiovisual(id) }
        }

    }

    @Test
    fun `should return successful response with titles when get all`() {
        runBlockingTest {
            // Given
            val titleResponse = TitleResponseStub.random()
            val titleResponse1 = TitleResponseStub.random()
            val titles = listOf(titleResponse, titleResponse1)
            coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
            coEvery { audiovisualApi.getAllTitles() } returns success(titles)
            // When
            val response = audiovisualDataSource.getAllTitles()
            // Then
            response shouldNotBe {null}
            response[0] shouldBe titleResponse
            response[0].id shouldBe titleResponse.id
            response[0].name shouldBe titleResponse.name
            response[1] shouldBe titleResponse1
            response[1].id shouldBe titleResponse1.id
            response[1].name shouldBe titleResponse1.name
        }
    }

    @Test
    fun `should return successful response with platforms when get all`() {
        runBlockingTest {
            // Given
            val platformResponse = PlatformResponseStub.random()
            val platformResponse1 = PlatformResponseStub.random()
            val platforms = listOf(platformResponse, platformResponse1)
            coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
            coEvery { audiovisualApi.getAllPlatforms() } returns success(platforms)
            // When
            val response = audiovisualDataSource.getAllPlatforms()
            // Then
            response shouldNotBe {null}
            response[0] shouldBe platformResponse
            response[0].id shouldBe platformResponse.id
            response[0].name shouldBe platformResponse.name
            response[0].url shouldBe platformResponse.url
            response[1] shouldBe platformResponse1
            response[1].id shouldBe platformResponse1.id
            response[1].name shouldBe platformResponse1.name
            response[1].url shouldBe platformResponse1.url
        }
    }

    @Test
    fun `should return empty body in response and throws exception when when create audiovisual`() = runBlockingTest {
        // Given
        val audiovisualRequest = AudiovisualStub.random().toRequest()
        every { provider.getAudiovisualDataSource() } returns audiovisualApi
        coEvery { audiovisualApi.createAudiovisual(any()) } returns success(null)
        // When & Then
        assertFailsWith<IllegalStateException>("Empty response body") {
            audiovisualDataSource.create(audiovisualRequest)
        }
    }

    @Test
    fun `should return unsuccessful response throws exception when when create audiovisual`() = runBlockingTest {
        // Given
        val audiovisualRequest = AudiovisualStub.random().toRequest()
        every { provider.getAudiovisualDataSource() } returns audiovisualApi
        coEvery { audiovisualApi.createAudiovisual(any()) } returns Response.error(
            400,
            ResponseBody.create(null, ByteArray(0))
        )
        // When & Then
        assertFailsWith<IOException>("Unsuccessful response") {
            audiovisualDataSource.create(audiovisualRequest)
        }
    }

}
