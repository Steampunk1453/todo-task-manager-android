package org.task.manager.data.network.source

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.api.GenreApi
import org.task.manager.stub.GenreResponseStub
import retrofit2.Response

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GenreRemoteDataSourceTest {

    @MockK
    private lateinit var provider: DataSourceProvider

    @MockK
    private lateinit var genreApi: GenreApi

    @InjectMockKs
    private lateinit var genreRemoteDataSource: GenreRemoteDataSource

    @Test
    fun `should return successful response with genres when get all`() {
        runBlockingTest {
            // Given
            val genreResponse = GenreResponseStub.random()
            val genreResponse1 = GenreResponseStub.random()
            val genres = listOf(genreResponse, genreResponse1)
            coEvery { provider.getGenreDataSource() } returns genreApi
            coEvery { genreApi.getGenres() } returns Response.success(genres)
            // When
            val response = genreRemoteDataSource.getAll()
            // Then
            response shouldNotBe {null}
            response[0] shouldBe genreResponse
            response[0].id shouldBe genreResponse.id
            response[0].name shouldBe genreResponse.name
            response[0].literary shouldBe genreResponse.literary
            response[1] shouldBe genreResponse1
            response[1].id shouldBe genreResponse1.id
            response[1].name shouldBe genreResponse1.name
            response[1].literary shouldBe genreResponse1.literary
        }
    }
}