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
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.api.AudiovisualApi
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.data.network.model.response.toResponse
import org.task.manager.stub.AudiovisualStub
import org.task.manager.stub.PlatformStub
import org.task.manager.stub.TitleStub
import retrofit2.Response
import retrofit2.Response.success
import java.io.IOException
import kotlin.random.Random
import kotlin.test.assertFailsWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class AudiovisualRemoteDataSourceTest {

    @MockK
    private lateinit var provider: DataSourceProvider

    @MockK
    private lateinit var audiovisualApi: AudiovisualApi

    @InjectMockKs
    private lateinit var audiovisualRemoteDataSource: AudiovisualRemoteDataSource

    @Test
    fun `should return successful audiovisual response when create`() =
            runBlockingTest {
                // Given
                val audiovisualRequest = AudiovisualStub.random().toRequest()
                val audiovisualResponse = AudiovisualStub.random().toResponse()
                coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
                coEvery { audiovisualApi.createAudiovisual(any()) } returns success(audiovisualResponse)
                // When
                val response = audiovisualRemoteDataSource.create(audiovisualRequest)
                // Then
                response shouldNotBe null
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
    fun `should return successful audiovisual response when update`() =
            runBlockingTest {
                // Given
                val audiovisualRequest = AudiovisualStub.random().toRequest()
                val audiovisualResponse = AudiovisualStub.random().toResponse()
                coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
                coEvery { audiovisualApi.updateAudiovisual(any()) } returns success(audiovisualResponse)
                // When
                val response = audiovisualRemoteDataSource.update(audiovisualRequest)
                // Then
                response shouldNotBe null
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
    fun `should return successful response with audiovisuals when get all`() =
            runBlockingTest {
                // Given
                val audiovisualResponse = AudiovisualStub.random().toResponse()
                val audiovisualResponse1 = AudiovisualStub.random().toResponse()
                val audiovisuals = listOf(audiovisualResponse, audiovisualResponse1)
                coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
                coEvery { audiovisualApi.getAllAudiovisuals() } returns success(audiovisuals)
                // When
                val response = audiovisualRemoteDataSource.findAll()
                // Then
                response shouldNotBe null
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

    @Test
    fun `should delete a audiovisual found by id`() =
            runBlockingTest {
                // Given
                val id = Random.nextLong(1, 5000)
                coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
                coEvery { audiovisualApi.deleteAudiovisual(any()) } returns success(null)
                audiovisualRemoteDataSource.delete(id)
                // Then
                coVerify(atLeast = 1) { audiovisualApi.deleteAudiovisual(id) }
            }


    @Test
    fun `should return successful response with titles when get all`() =
            runBlockingTest {
                // Given
                val titleResponse = TitleStub.random().toResponse()
                val titleResponse1 = TitleStub.random().toResponse()
                val titles = listOf(titleResponse, titleResponse1)
                coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
                coEvery { audiovisualApi.getAllTitles() } returns success(titles)
                // When
                val response = audiovisualRemoteDataSource.getAllTitles()
                // Then
                response shouldNotBe null
                response[0] shouldBe titleResponse
                response[0].id shouldBe titleResponse.id
                response[0].title shouldBe titleResponse.title
                response[0].rank shouldBe titleResponse.rank
                response[0].type shouldBe titleResponse.type
                response[0].genres shouldBe titleResponse.genres
                response[0].platform shouldBe titleResponse.platform
                response[0].website shouldBe titleResponse.website
                response[1] shouldBe titleResponse1
                response[1].id shouldBe titleResponse1.id
                response[1].title shouldBe titleResponse1.title
                response[1].rank shouldBe titleResponse1.rank
                response[1].type shouldBe titleResponse1.type
                response[1].genres shouldBe titleResponse1.genres
                response[1].platform shouldBe titleResponse1.platform
                response[1].website shouldBe titleResponse1.website
            }

    @Test
    fun `should return successful response with platforms when get all`() =
            runBlockingTest {
                // Given
                val platformResponse = PlatformStub.random().toResponse()
                val platformResponse1 = PlatformStub.random().toResponse()
                val platforms = listOf(platformResponse, platformResponse1)
                coEvery { provider.getAudiovisualDataSource() } returns audiovisualApi
                coEvery { audiovisualApi.getAllPlatforms() } returns success(platforms)
                // When
                val response = audiovisualRemoteDataSource.getAllPlatforms()
                // Then
                response shouldNotBe null
                response[0] shouldBe platformResponse
                response[0].id shouldBe platformResponse.id
                response[0].name shouldBe platformResponse.name
                response[0].url shouldBe platformResponse.url
                response[1] shouldBe platformResponse1
                response[1].id shouldBe platformResponse1.id
                response[1].name shouldBe platformResponse1.name
                response[1].url shouldBe platformResponse1.url
            }

    @Test
    fun `should return empty body in response and throws exception when create audiovisual`() =
            runBlockingTest {
                // Given
                val audiovisualRequest = AudiovisualStub.random().toRequest()
                every { provider.getAudiovisualDataSource() } returns audiovisualApi
                coEvery { audiovisualApi.createAudiovisual(any()) } returns success(null)
                // When & Then
                assertFailsWith<IllegalStateException>("Empty response body") {
                    audiovisualRemoteDataSource.create(audiovisualRequest)
                }
            }

    @Test
    fun `should return unsuccessful response throws exception when create audiovisual`() =
            runBlockingTest {
                // Given
                val audiovisualRequest = AudiovisualStub.random().toRequest()
                every { provider.getAudiovisualDataSource() } returns audiovisualApi
                coEvery { audiovisualApi.createAudiovisual(any()) } returns Response.error(
                        400,
                    ByteArray(0).toResponseBody(null)
                )
                // When & Then
                assertFailsWith<IOException>("Unsuccessful response") {
                    audiovisualRemoteDataSource.create(audiovisualRequest)
                }
            }

}
