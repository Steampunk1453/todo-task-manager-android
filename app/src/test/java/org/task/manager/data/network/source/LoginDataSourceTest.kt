package org.task.manager.data.network.source

import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.api.LoginApi
import org.task.manager.stub.LoginRequestStub
import org.task.manager.stub.LoginResponseStub
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
    fun `should return successful login response when login a user`() = runBlockingTest {
        // Given
        val loginRequest = LoginRequestStub.random()
        val loginResponse = LoginResponseStub.random()
        every { provider.getLoginDataSource() } returns loginApi
        coEvery { loginApi.login(any()) } returns success(loginResponse)
        // When
        val response = loginDataSource.login(loginRequest)
        response.statusCode shouldBe 201
        response.authToken shouldBe loginResponse.authToken
    }

    @Test
    fun `should return empty body in response and throws exception when login a user`() = runBlockingTest {
        // Given
        val loginRequest = LoginRequestStub.random()
        every { provider.getLoginDataSource() } returns loginApi
        coEvery { loginApi.login(any()) } returns success(null)
        // When & Then
        assertFailsWith<IllegalStateException>("Empty response body") {
            loginDataSource.login(loginRequest)
        }
    }

    @Test
    fun `should return unsuccessful response throws exception when login a user`() = runBlockingTest {
        // Given
        val loginRequest = LoginRequestStub.random()
        every { provider.getLoginDataSource() } returns loginApi
        coEvery { loginApi.login(any()) } returns Response.error(
            400,
            ByteArray(0).toResponseBody(null)
        )
        // When & Then
        assertFailsWith<IOException>("Unsuccessful response") {
            loginDataSource.login(loginRequest)
        }
    }

}