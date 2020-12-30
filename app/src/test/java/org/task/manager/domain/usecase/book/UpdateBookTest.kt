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
internal class UpdateBookTest {

    @MockK
    private lateinit var repository: BookRepository

    @InjectMockKs
    private lateinit var useCase: UpdateBook

    @Test
    fun `should return book when execute update`() = runBlockingTest  {
        // Given
        val book = BookStub.random()
        coEvery { repository.update(any()) } returns Result.Success(book)
        // When
        val result = useCase.execute(book)
        // Then
        result shouldNotBe null
        result?.id shouldBe book.id
        result?.title shouldBe book.title
        result?.genre shouldBe book.genre
        result?.editorial shouldBe book.editorial
        result?.editorialUrl shouldBe book.editorialUrl
        result?.bookshop shouldBe book.bookshop
        result?.bookshopUrl shouldBe book.bookshopUrl
        result?.startDate shouldBe book.startDate
        result?.deadline shouldBe book.deadline
        result?.check shouldBe book.check
        result?.user?.id shouldBe book.user?.id
    }

    @Test
    fun `should return null book when execute update`() = runBlockingTest  {
        // Given
        val book = BookStub.random()
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.update(any()) } returns Result.Error(error)
        // When
        val result = useCase.execute(book)
        // Then
        result shouldBe null
    }

}