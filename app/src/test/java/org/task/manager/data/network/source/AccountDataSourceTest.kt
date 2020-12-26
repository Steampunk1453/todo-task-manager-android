package org.task.manager.data.network.source

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.api.AccountApi
import org.task.manager.data.network.model.request.toRequest
import org.task.manager.stub.PasswordRequestStub
import org.task.manager.stub.RegisterRequestStub
import org.task.manager.stub.ResetPasswordRequestStub
import org.task.manager.stub.UserResponseStub
import org.task.manager.stub.UserStub
import retrofit2.Response
import retrofit2.Response.success
import java.io.IOException
import kotlin.test.assertFailsWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class AccountDataSourceTest {

    @MockK
    private lateinit var provider: DataSourceProvider

    @MockK
    private lateinit var accountApi: AccountApi

    @InjectMockKs
    private lateinit var accountDataSource: AccountDataSource

    @Test
    fun `should call accountApi when user is registered`() = runBlockingTest {
        // Given
        val registerRequest = RegisterRequestStub.random()
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.register(registerRequest) } just Runs
        // When
        accountDataSource.register(registerRequest)
        // Then
        coVerify { accountApi.register(any()) }
    }

    @Test
    fun `should call accountApi when user is activated`() = runBlockingTest {
        // Given
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.activate(any()) } just Runs
        // When
        accountDataSource.activate("key")
        // Then
        coVerify { accountApi.activate(any()) }
    }

    @Test
    fun `should return user response when get user account`() = runBlockingTest {
        // Given
        val userResponse = UserResponseStub.random()
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.get() } returns success(userResponse)
        // When
        val response = accountDataSource.get()
        // Then
        response shouldNotBe null
        response shouldBe userResponse
        response.id shouldBe userResponse.id
        response.username shouldBe userResponse.username
        response.email shouldBe userResponse.email
        response.firstName shouldBe userResponse.firstName
        response.lastName shouldBe userResponse.lastName
    }

    @Test
    fun `should return empty body in response and throws exception when get user account`() = runBlockingTest {
        // Given
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.get() } returns success(null)
        // When & Then
        assertFailsWith<IllegalStateException>("Empty response body") {
            accountDataSource.get()
        }
    }

    @Test
    fun `should return unsuccessful response throws exception when get user account`() = runBlockingTest {
        // Given
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.get() } returns Response.error(
            400,
            ResponseBody.create(null, ByteArray(0))
        )
        // When
        // When & Then
        assertFailsWith<IOException>("Unsuccessful response") {
            accountDataSource.get()
        }
    }

    @Test
    fun `should call accountApi when save user`() = runBlockingTest {
        // Given
        val userRequest = UserStub.random().toRequest()
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.save(any()) } just Runs
        // When
        accountDataSource.save(userRequest)
        // Then
        coVerify { accountApi.save(any()) }
    }

    @Test
    fun `should call accountApi when change user password`() = runBlockingTest {
        // Given
        val passwordRequest = PasswordRequestStub.random()
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.changePassword(any()) } just Runs
        // When
        accountDataSource.changePassword(passwordRequest)
        // Then
        coVerify { accountApi.changePassword(any()) }
    }

    @Test
    fun `should call accountApi when request user password reset`() = runBlockingTest {
        // Given
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.requestPasswordReset(any()) } just Runs
        // When
        accountDataSource.requestPasswordReset("mail")
        // Then
        coVerify { accountApi.requestPasswordReset(any()) }
    }

    @Test
    fun `should call accountApi when finish user password reset`() = runBlockingTest {
        // Given
        val resetPasswordRequest = ResetPasswordRequestStub.random()
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.finishPasswordReset(any()) } just Runs
        // When
        accountDataSource.finishPasswordReset(resetPasswordRequest)
        // Then
        coVerify { accountApi.finishPasswordReset(any()) }
    }

}