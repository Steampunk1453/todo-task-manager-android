package org.task.manager.domain.usecase.audiovisual

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
import org.task.manager.domain.repository.AudiovisualRepository
import org.task.manager.shared.Constants
import org.task.manager.stub.PlatformStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GetPlatformsTest {

    @MockK
    private lateinit var repository: AudiovisualRepository

    @InjectMockKs
    private lateinit var useCase: GetPlatforms

    @Test
    fun `should return platforms when execute`() = runBlockingTest  {
        // Given
        val platform = PlatformStub.random()
        val platforms = listOf(platform)
        coEvery { repository.getAllPlatforms() } returns Result.Success(platforms)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.get(0)?.id shouldBe platform.id
        result?.get(0)?.name shouldBe platform.name
        result?.get(0)?.url shouldBe platform.url
    }

    @Test
    fun `should return empty platforms list when execute`() = runBlockingTest  {
        // Given
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.getAllPlatforms() } returns Result.Error(error)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result shouldBe emptyList()
    }

}