package org.task.manager.data.repository

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.task.manager.data.local.source.AudiovisualLocalDataSource
import org.task.manager.data.network.source.AudiovisualRemoteDataSource

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class AudiovisualRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var remoteDataSource: AudiovisualRemoteDataSource

    @MockK(relaxed = true)
    private lateinit var localDataSource: AudiovisualLocalDataSource

    @InjectMockKs
    private lateinit var audiovisualRepository: DefaultAudiovisualRepository

    @BeforeEach
    fun setup() {
        audiovisualRepository = DefaultAudiovisualRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun save() {
    }

    @Test
    fun update() {
    }

    @Test
    fun getAll() {
    }

    @Test
    fun remove() {
    }

    @Test
    fun getAllTitles() {
    }

    @Test
    fun getAllPlatforms() {
    }
}