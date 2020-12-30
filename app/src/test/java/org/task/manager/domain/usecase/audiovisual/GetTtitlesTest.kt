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
import org.task.manager.stub.TitleStub

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class GetTtitlesTest {

    @MockK
    private lateinit var repository: AudiovisualRepository

    @InjectMockKs
    private lateinit var useCase: GetTitles

    @Test
    fun `should return titles when execute`() = runBlockingTest  {
        // Given
        val title = TitleStub.random()
        val titles = listOf(title)
        coEvery { repository.getAllTitles() } returns Result.Success(titles)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result?.get(0)?.id shouldBe title.id
        result?.get(0)?.name shouldBe title.name
    }

    @Test
    fun `should return empty titles list when execute`() = runBlockingTest  {
        // Given
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.getAllTitles() } returns Result.Error(error)
        // When
        val result = useCase.execute()
        // Then
        result shouldNotBe null
        result shouldBe emptyList()
    }

}