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
        val type = titles[0].type
        coEvery { repository.getAllTitles() } returns Result.Success(titles)
        // When
        val result = useCase.execute(type)
        // Then
        result shouldNotBe null
        result[0].id shouldBe title.id
        result[0].title shouldBe title.title
        result[0].rank shouldBe title.rank
        result[0].type shouldBe title.type
        result[0].genres shouldBe title.genres
        result[0].platform shouldBe title.platform
        result[0].website shouldBe title.website
    }

    @Test
    fun `should return empty titles list when execute`() = runBlockingTest  {
        // Given
        val type = "TVSeries"
        val error = Throwable(Constants.ILLEGAL_STATE_EXCEPTION_CAUSE)
        coEvery { repository.getAllTitles() } returns Result.Error(error)
        // When
        val result = useCase.execute(type)
        // Then
        result shouldNotBe null
        result shouldBe emptyList()
    }

}