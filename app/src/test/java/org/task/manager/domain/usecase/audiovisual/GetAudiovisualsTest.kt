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
import org.task.manager.stub.AudiovisualStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GetAudiovisualsTest {

    @MockK
    private lateinit var repository: AudiovisualRepository

    @InjectMockKs
    private lateinit var useCase: GetAudiovisuals

    @Test
    fun `should return audiovisuals when execute`() = runBlockingTest  {
        // Given
        val audiovisual = AudiovisualStub.random()
        val audiovisuals = listOf(audiovisual)
        coEvery { repository.getAll() } returns Result.Success(audiovisuals)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.get(0)?.id shouldBe audiovisual.id
        result?.get(0)?.title shouldBe audiovisual.title
        result?.get(0)?.genre shouldBe audiovisual.genre
        result?.get(0)?.platform shouldBe audiovisual.platform
        result?.get(0)?.platformUrl shouldBe audiovisual.platformUrl
        result?.get(0)?.startDate shouldBe audiovisual.startDate
        result?.get(0)?.deadline shouldBe audiovisual.deadline
        result?.get(0)?.check shouldBe audiovisual.check
        result?.get(0)?.user?.id shouldBe audiovisual.user?.id
    }

    @Test
    fun `should return empty audiovisuals list when execute`() = runBlockingTest  {
        // Given
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.getAll() } returns Result.Error(error)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result shouldBe emptyList()
    }

}