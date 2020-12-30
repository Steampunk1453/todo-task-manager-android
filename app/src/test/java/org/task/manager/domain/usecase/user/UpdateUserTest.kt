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
import org.task.manager.domain.model.state.AccountState
import org.task.manager.domain.repository.AccountRepository
import org.task.manager.shared.Constants
import org.task.manager.stub.UserStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class UpdateUserTest {

    @MockK
    private lateinit var repository: AccountRepository

    @InjectMockKs
    private lateinit var useCase: UpdateUser

    @Test
    fun `should return UPDATE_COMPLETED state when execute with user`() = runBlockingTest  {
        // Given
        val user = UserStub.random()
        val expected = AccountState.UPDATE_COMPLETED
        coEvery { repository.save(any()) } returns Result.Success("OK")
        // When
        val result = useCase.execute(user)
        // Then
        result shouldNotBe null
        result shouldBe expected
    }

    @Test
    fun `should return INVALID_UPDATE state when execute with user`() = runBlockingTest  {
        // Given
        val user = UserStub.random()
        val expected = AccountState.INVALID_UPDATE
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.save(any()) } returns Result.Error(error)
        // When
        val result = useCase.execute(user)
        // Then
        result shouldNotBe null
        result shouldBe expected
    }

}