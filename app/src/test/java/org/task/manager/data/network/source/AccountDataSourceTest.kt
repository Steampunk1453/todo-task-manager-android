package org.task.manager.data.network.source

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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.api.AccountApi
import org.task.manager.stub.RegisterRequestStub

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
    fun `should no returns error response`() = runBlockingTest {
        // Given
        val registerRequest = RegisterRequestStub.random()
        every { provider.getAccountDataSource() } returns accountApi
        coEvery { accountApi.register(registerRequest) } just Runs
        // When
        accountDataSource.register(registerRequest)
        // Then
        coVerify { accountApi.register(registerRequest) }
    }
}