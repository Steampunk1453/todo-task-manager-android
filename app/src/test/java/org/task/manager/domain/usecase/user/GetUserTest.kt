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
import org.task.manager.domain.repository.AccountRepository
import org.task.manager.shared.Constants
import org.task.manager.stub.UserStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GetUserTest {

    @MockK
    private lateinit var repository: AccountRepository

    @InjectMockKs
    private lateinit var useCase: GetUser

    @Test
    fun `should return user when execute`() = runBlockingTest  {
        // Given
        val user = UserStub.random()
        coEvery { repository.get() } returns Result.Success(user)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.id shouldBe user.id
        result?.username shouldBe user.username
        result?.email shouldBe user.email
        result?.firstName shouldBe user.firstName
        result?.lastName shouldBe user.lastName
    }

    @Test
    fun `should return empty user when execute`() = runBlockingTest  {
        // Given
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.get() } returns Result.Error(error)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.id shouldBe null
        result?.username shouldBe null
        result?.email shouldBe null
        result?.firstName shouldBe null
        result?.lastName shouldBe null
    }

}