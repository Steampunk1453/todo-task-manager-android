package org.task.manager.domain.usecase.book

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
import org.task.manager.domain.repository.BookRepository
import org.task.manager.shared.Constants
import org.task.manager.stub.EditorialStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GetEditorialsTest {

    @MockK
    private lateinit var repository: BookRepository

    @InjectMockKs
    private lateinit var useCase: GetEditorials

    @Test
    fun `should return editorials when execute`() = runBlockingTest  {
        // Given
        val editorial = EditorialStub.random()
        val editorials = listOf(editorial)
        coEvery { repository.getAllEditorials() } returns Result.Success(editorials)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.get(0)?.id shouldBe editorial.id
        result?.get(0)?.name shouldBe editorial.name
        result?.get(0)?.url shouldBe editorial.url
    }

    @Test
    fun `should return empty editorials list when execute`() = runBlockingTest  {
        // Given
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.getAllEditorials() } returns Result.Error(error)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result shouldBe emptyList()
    }

}