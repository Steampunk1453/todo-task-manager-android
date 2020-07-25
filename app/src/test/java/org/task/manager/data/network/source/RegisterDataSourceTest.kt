package org.task.manager.data.network.source

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
import org.task.manager.data.network.api.RegisterApi
import org.task.manager.data.network.model.request.RegisterRequest

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class RegisterDataSourceTest {

    @MockK
    private lateinit var provider: DataSourceProvider

    @MockK
    private lateinit var registerApi: RegisterApi

    @InjectMockKs
    private lateinit var registerDataSource: RegisterDataSource

    @Test
    fun `should no returns error response`() = runBlockingTest {
        // Given
        val registerRequest = RegisterRequest("login", "email", "password", "en")
        coEvery { provider.getRegisterDataSource() } returns registerApi
        coEvery { registerApi.register(registerRequest) } just Runs
        // When
        registerDataSource.register(registerRequest)
        // Then
        coVerify { registerApi.register(registerRequest) }
    }
}