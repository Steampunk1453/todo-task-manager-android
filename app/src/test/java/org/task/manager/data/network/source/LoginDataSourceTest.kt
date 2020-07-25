package org.task.manager.data.network.source

import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.api.LoginApi
import org.task.manager.data.network.model.request.LoginRequest
import org.task.manager.data.network.model.response.LoginResponse
import retrofit2.Response
import retrofit2.Response.success
import java.io.IOException
import kotlin.test.assertFailsWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class LoginDataSourceTest {

    @MockK
    private lateinit var provider: DataSourceProvider

    @MockK
    private lateinit var loginApi: LoginApi

    @InjectMockKs
    private lateinit var loginDataSource: LoginDataSource

    @Test
    fun `should returns successful response`() = runBlockingTest {
        // Given
        val loginRequest = LoginRequest("username", "password", false)
        val loginResponse = LoginResponse(statusCode = 201, authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
        coEvery { provider.getLoginDataSource() } returns loginApi
        coEvery { loginApi.login(any()) } returns success(loginResponse)
        // When
        val response = loginDataSource.login(loginRequest)
        // Then
        assertThat(response).isEqualTo(loginResponse)
    }

    @Test
    fun `should returns empty body in response and throws exception`() = runBlockingTest {
        // Given
        val loginRequest = LoginRequest("username", "password", false)
        coEvery { provider.getLoginDataSource() } returns loginApi
        coEvery { loginApi.login(any()) } returns success(null)
        // When & Then
        assertFailsWith<IllegalStateException>("Empty response body") {
            loginDataSource.login(loginRequest)
        }
    }

    @Test
    fun `should returns unsuccessful response throws exception`() = runBlockingTest {
        // Given
        val loginRequest = LoginRequest("username", "password", false)
        coEvery { provider.getLoginDataSource() } returns loginApi
        coEvery { loginApi.login(any()) } returns Response.error(
            400,
            ResponseBody.create(null, ByteArray(0))
        )
        // When & Then
        assertFailsWith<IOException>("Unsuccessful response") {
            loginDataSource.login(loginRequest)
        }
    }

}