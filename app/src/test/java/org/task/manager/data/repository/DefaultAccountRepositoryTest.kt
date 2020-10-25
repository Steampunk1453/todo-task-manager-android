package org.task.manager.data.repository

import io.kotlintest.shouldBe
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
import org.task.manager.data.network.source.AccountDataSource
import org.task.manager.domain.Result
import org.task.manager.shared.Constants
import org.task.manager.stub.RegisterRequestStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class DefaultAccountRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var dataSource: AccountDataSource

    @InjectMockKs
    private lateinit var accountRepository: DefaultAccountRepository

    @BeforeEach
    fun setup() {
        accountRepository = DefaultAccountRepository(dataSource)
    }

    @Test
    fun `should return successful result when register a user`() = runBlockingTest {
        // Given
        val request = RegisterRequestStub.random()
        val expected = Result.Success("OK")
        coEvery { dataSource.register(any()) } just Runs
        // When
        val result = accountRepository.register(request)
        // Then
        coVerify { accountRepository.register(request) }

        result shouldBe expected
        result as Result.Success
        result.data shouldBe "OK"
    }

    @Test
    fun `should return result with error message when register a user`() = runBlockingTest {
        // Given
        val request = RegisterRequestStub.random()
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { dataSource.register(any()) } throws error
        // When
        val result = accountRepository.register(request)
        // Then
        coVerify { accountRepository.register(request) }

        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
    }

}