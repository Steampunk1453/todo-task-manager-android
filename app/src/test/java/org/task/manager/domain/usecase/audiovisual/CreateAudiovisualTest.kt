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
internal class CreateAudiovisualTest {

    @MockK
    private lateinit var repository: AudiovisualRepository

    @InjectMockKs
    private lateinit var useCase: CreateAudiovisual

    @Test
    fun `should return audiovisual when execute create`() = runBlockingTest  {
        // Given
        val audiovisual = AudiovisualStub.random()
        coEvery { repository.save(any()) } returns Result.Success(audiovisual)
        // When
        val result = useCase.execute(audiovisual)
        // Then
        result shouldNotBe null
        result?.id shouldBe audiovisual.id
        result?.title shouldBe audiovisual.title
        result?.genre shouldBe audiovisual.genre
        result?.platform shouldBe audiovisual.platform
        result?.platformUrl shouldBe audiovisual.platformUrl
        result?.startDate shouldBe audiovisual.startDate
        result?.deadline shouldBe audiovisual.deadline
        result?.check shouldBe audiovisual.check
        result?.user?.id shouldBe audiovisual.user?.id
    }

    @Test
    fun `should return null audiovisual when execute create`() = runBlockingTest  {
        // Given
        val audiovisual = AudiovisualStub.random()
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.save(any()) } returns Result.Error(error)
        // When
        val result = useCase.execute(audiovisual)
        // Then
        result shouldBe null
    }

}