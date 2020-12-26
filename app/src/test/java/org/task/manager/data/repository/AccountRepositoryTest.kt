package org.task.manager.data.repository

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.model.response.toResponse
import org.task.manager.data.network.source.AccountDataSource
import org.task.manager.domain.Result
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import org.task.manager.stub.PasswordRequestStub
import org.task.manager.stub.ResetPasswordRequestStub
import org.task.manager.stub.UserStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class AccountRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var dataSource: AccountDataSource

    @InjectMockKs
    private lateinit var accountRepository: DefaultAccountRepository

    @BeforeEach
    fun setup() {
        accountRepository = DefaultAccountRepository(dataSource)
    }

    @Test
    fun `should return successful result when register user`() = runBlockingTest {
        // Given
        val user = UserStub.random()
        val expected = Result.Success("OK")
        coEvery { dataSource.register(any()) } just Runs
        // When
        val result = accountRepository.register(user)
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Success
        result.data shouldBe "OK"
    }

    @Test
    fun `should return result with error message when register user`() = runBlockingTest {
        // Given
        val user = UserStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { dataSource.register(any()) } throws error
        // When
        val result = accountRepository.register(user)
        // Then
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when activate user`() = runBlockingTest {
        // Given
        val expected = Result.Success("OK")
        coEvery { dataSource.activate(any()) } just Runs
        // When
        val result = accountRepository.activate("key")
        // Then
        result shouldNotBe null
        result shouldBe expected
        result as Result.Success
        result.data shouldBe "OK"
    }

    @Test
    fun `should return result with error message when activate user`() = runBlockingTest {
        // Given
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { dataSource.activate(any()) } throws error
        // When
        val result = accountRepository.activate("key")
        // Then
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result with user when get account info`() = runBlockingTest {
        // Given
        val userResponse = UserStub.random().toResponse()
        val expected = Result.Success(userResponse)
        coEvery { dataSource.get() } returns userResponse
        // When
        val result = accountRepository.get()
        // Then
        result shouldNotBe null
        result as Result.Success
        result.data.id shouldBe expected.data.id
        result.data.username shouldBe expected.data.username
        result.data.email shouldBe expected.data.email
        result.data.firstName shouldBe expected.data.firstName
        result.data.lastName shouldBe expected.data.lastName
    }

    @Test
    fun `should return result with error message when get account info`() = runBlockingTest {
        // Given
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { dataSource.get() } throws error
        // When
        val result = accountRepository.get()
        // Then
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when save user`() = runBlockingTest {
        // Given
        val user = UserStub.random()
        val expected = Result.Success("OK")
        coEvery { dataSource.save(any()) } just Runs
        // When
        val result = accountRepository.save(user)
        // Then
        result shouldBe expected
        result as Result.Success
        result.data shouldBe "OK"
    }

    @Test
    fun `should return result with error message when save user`() = runBlockingTest {
        // Given
        val user = UserStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { dataSource.save(any()) } throws error
        // When
        val result = accountRepository.save(user)
        // Then
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when change a user password`() = runBlockingTest {
        // Given
        val passwordRequest = PasswordRequestStub.random()
        val expected = Result.Success("OK")
        coEvery { dataSource.changePassword(any()) } just Runs
        // When
        val result = accountRepository.changePassword(passwordRequest)
        // Then
        result shouldBe expected
        result as Result.Success
        result.data shouldBe "OK"
    }

    @Test
    fun `should return result with error message when change a user password`() = runBlockingTest {
        // Given
        val passwordRequest = PasswordRequestStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { dataSource.changePassword(any()) } throws error
        // When
        val result = accountRepository.changePassword(passwordRequest)
        // Then
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

    @Test
    fun `should return successful result when finish password user reset`() = runBlockingTest {
        // Given
        val resetPasswordRequest = ResetPasswordRequestStub.random()
        val expected = Result.Success("OK")
        coEvery { dataSource.changePassword(any()) } just Runs
        // When
        val result = accountRepository.finishPasswordReset(resetPasswordRequest)
        // Then
        result shouldBe expected
        result as Result.Success
        result.data shouldBe "OK"
    }

    @Test
    fun `should return result with error message when change user password`() = runBlockingTest {
        // Given
        val resetPasswordRequest = ResetPasswordRequestStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { dataSource.finishPasswordReset(any()) } throws error
        // When
        val result = accountRepository.finishPasswordReset(resetPasswordRequest)
        // Then
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

}