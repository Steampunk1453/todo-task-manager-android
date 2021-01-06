package org.task.manager.domain.usecase.user

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.domain.Result
import org.task.manager.domain.model.state.AuthenticationState
import org.task.manager.domain.repository.LoginRepository
import org.task.manager.shared.Constants
import org.task.manager.shared.service.SessionManagerService
import org.task.manager.stub.LoginRequestStub
import org.task.manager.stub.LoginResponseStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class LoginUserTest {

    @MockK
    private lateinit var repository: LoginRepository

    @MockK
    private lateinit var sessionManager: SessionManagerService

    @InjectMockKs
    private lateinit var useCase: LoginUser

    @Test
    fun `should return AUTHENTICATED state with successful message when execute with login request`() = runBlockingTest  {
        // Given
        val loginRequest = LoginRequestStub.random()
        val loginResponse = LoginResponseStub.random()
        val expected = AuthenticationState.AUTHENTICATED
        coEvery { repository.login(any()) } returns Result.Success(loginResponse)
        coEvery { sessionManager.saveAuthToken(any()) } just Runs
        // When
        val result = useCase.execute(loginRequest)
        // Then
        coVerify { sessionManager.saveAuthToken(any()) }
        result shouldNotBe null
        result shouldBe expected
    }

    @Test
    fun `should return INVALID_AUTHENTICATION state with error message when execute with login request`() = runBlockingTest  {
        // Given
        val loginRequest = LoginRequestStub.random()
        val expected = AuthenticationState.INVALID_AUTHENTICATION
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.login(any()) } returns Result.Error(error)
        // When
        val result = useCase.execute(loginRequest)
        // Then
        result shouldNotBe null
        result shouldBe expected
    }

}