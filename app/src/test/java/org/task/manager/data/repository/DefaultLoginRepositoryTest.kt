package org.task.manager.data.repository

import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import org.task.manager.data.network.source.LoginDataSource
import org.task.manager.domain.Result

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
    fun `should returns result with login response`() = runBlockingTest {
        // Given
        val loginRequest = LoginRequest("username", "password", false)
        val loginResponse = LoginResponse(statusCode = 201, authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
        val result = Result.Success(loginResponse)
        coEvery { dataSource.login(any()) } returns loginResponse
        // When
        val response = loginRepository.login(loginRequest)
        // Then
        Assertions.assertThat(response).isEqualTo(result)
    }

}