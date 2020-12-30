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
import org.task.manager.stub.BookStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GetBooksTest {

    @MockK
    private lateinit var repository: BookRepository

    @InjectMockKs
    private lateinit var useCase: GetBooks

    @Test
    fun `should return books when execute`() = runBlockingTest  {
        // Given
        val book = BookStub.random()
        val books = listOf(book)
        coEvery { repository.getAll() } returns Result.Success(books)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.get(0)?.id shouldBe book.id
        result?.get(0)?.title shouldBe book.title
        result?.get(0)?.genre shouldBe book.genre
        result?.get(0)?.editorial shouldBe book.editorial
        result?.get(0)?.editorialUrl shouldBe book.editorialUrl
        result?.get(0)?.bookshop shouldBe book.bookshop
        result?.get(0)?.bookshopUrl shouldBe book.bookshopUrl
        result?.get(0)?.startDate shouldBe book.startDate
        result?.get(0)?.deadline shouldBe book.deadline
        result?.get(0)?.check shouldBe book.check
        result?.get(0)?.user?.id shouldBe book.user?.id
    }

    @Test
    fun `should return empty books list when execute`() = runBlockingTest  {
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