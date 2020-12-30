package org.task.manager.domain.usecase.shared

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
import org.task.manager.domain.Result
import org.task.manager.domain.repository.GenreRepository
import org.task.manager.shared.Constants
import org.task.manager.stub.GenreStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GetGenresTest {

    @MockK
    private lateinit var repository: GenreRepository

    @InjectMockKs
    private lateinit var useCase: GetGenres

    @Test
    fun `should return genres when execute`() = runBlockingTest  {
        // Given
        val genre = GenreStub.random()
        val genres = listOf(genre)
        coEvery { repository.getAll() } returns Result.Success(genres)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.get(0)?.id shouldBe genre.id
        result?.get(0)?.name shouldBe genre.name
        result?.get(0)?.isLiterary shouldBe genre.isLiterary
    }

    @Test
    fun `should return empty genres list when execute`() = runBlockingTest  {
        // Given
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.getAll() } returns Result.Error(error)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result shouldBe emptyList()
    }

}