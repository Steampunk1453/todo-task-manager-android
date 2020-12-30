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
import org.task.manager.stub.BookshopStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GetBookshopsTest {

    @MockK
    private lateinit var repository: BookRepository

    @InjectMockKs
    private lateinit var useCase: GetBookshops

    @Test
    fun `should return bookshops when execute`() = runBlockingTest  {
        // Given
        val bookshop = BookshopStub.random()
        val bookshops = listOf(bookshop)
        coEvery { repository.getAllBookshops() } returns Result.Success(bookshops)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.get(0)?.id shouldBe bookshop.id
        result?.get(0)?.name shouldBe bookshop.name
        result?.get(0)?.url shouldBe bookshop.url
    }

    @Test
    fun `should return empty bookshops list when execute`() = runBlockingTest  {
        // Given
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.getAllBookshops() } returns Result.Error(error)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result shouldBe emptyList()
    }

}