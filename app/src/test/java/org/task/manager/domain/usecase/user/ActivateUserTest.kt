package org.task.manager.domain.usecase.user

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.domain.Result
import org.task.manager.domain.model.state.RegistrationState
import org.task.manager.domain.repository.AccountRepository
import org.task.manager.shared.Constants

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class ActivateUserTest {

    @MockK
    private lateinit var repository: AccountRepository

    @InjectMockKs
    private lateinit var useCase: ActivateUser

    @Test
    fun `should return ACTIVATION_COMPLETED state when execute with activate key`() = runBlockingTest  {
        // Given
        val expected = RegistrationState.ACTIVATION_COMPLETED
        coEvery { repository.activate(any()) } returns Result.Success( "OK")
        // When
        val result = useCase.execute("key")
        // Then
        result shouldNotBe null
        result shouldBe expected
    }

    @Test
    fun `should return INVALID_ACTIVATION state when execute with activate key`() = runBlockingTest  {
        // Given
        val expected = RegistrationState.INVALID_ACTIVATION
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.activate(any()) } returns Result.Error(error)
        // When
        val result = useCase.execute("key")
        // Then
        result shouldNotBe null
        result shouldBe expected
    }

}