package org.task.manager.data.repository

import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.domain.Result
import org.task.manager.shared.Constants.ILLEGAL_STATE_EXCEPTION_CAUSE
import org.task.manager.stub.LoginRequestStub
import org.task.manager.stub.LoginResponseStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class DefaultLoginRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var dataSource: LoginDataSource

    @InjectMockKs
    private lateinit var loginRepository: DefaultLoginRepository

    @BeforeEach
    fun setup() {
        loginRepository = DefaultLoginRepository(dataSource)
    }


    @Test
    fun `should return result with login response when login a user`() = runBlockingTest {
        // Given
        val request = LoginRequestStub.random()
        val response = LoginResponseStub.random()
        val expected = Result.Success(response)
        coEvery { dataSource.login(any()) } returns response
        // When
        val result = loginRepository.login(request)
        // Then
        result shouldBe expected
        result as Result.Success
        result.data.statusCode shouldBe response.statusCode
        result.data.authToken shouldBe response.authToken
    }

    @Test
    fun `should return result with error message when login a user`() = runBlockingTest {
        // Given
        val request = LoginRequestStub.random()
        val error = Throwable(ILLEGAL_STATE_EXCEPTION_CAUSE)
        val expected = Result.Error(error)
        coEvery { dataSource.login(any()) } throws error
        // When
        val result = loginRepository.login(request)
        // Then
        result shouldBe expected
        result as Result.Error
        result.throwable.message shouldBe ILLEGAL_STATE_EXCEPTION_CAUSE
    }

}