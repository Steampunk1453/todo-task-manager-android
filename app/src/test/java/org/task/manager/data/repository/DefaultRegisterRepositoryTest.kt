package org.task.manager.data.repository

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

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.network.model.request.RegisterRequest
import org.task.manager.data.network.source.RegisterDataSource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.task.manager.domain.Result
import java.io.IOException
import kotlin.test.assertFailsWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class DefaultRegisterRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var dataSource: RegisterDataSource

    @InjectMockKs
    private lateinit var registerRepository: DefaultRegisterRepository

    @BeforeEach
    fun setup() {
        registerRepository = DefaultRegisterRepository(dataSource)
    }

    @Test
    fun `should returns register result when register a request`() = runBlockingTest {
        // Given
        val registerRequest = RegisterRequest("login", "email", "password", "en")
        coEvery { dataSource.register(registerRequest) } just Runs
        // When
        registerRepository.register(registerRequest)
        // Then
        coVerify { registerRepository.register(registerRequest) }
    }

}