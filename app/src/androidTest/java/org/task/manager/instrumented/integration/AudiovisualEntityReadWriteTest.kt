package org.task.manager.instrumented.integration

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.task.manager.data.local.AppDatabase
import org.task.manager.data.local.dao.AudiovisualDao
import org.task.manager.data.local.model.AudiovisualEntity
import org.task.manager.data.local.model.toEntity
import org.task.manager.instrumented.integration.AudiovisualStubIT.Companion.getNewAudiovisual
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AudiovisualEntityReadWriteTest {

    private lateinit var audiovisualDao: AudiovisualDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        audiovisualDao = db.audiovisualDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAudiovisualAndReadInList() {
        val audiovisual: AudiovisualEntity = AudiovisualStubIT.getAudiovisual().toEntity()

        audiovisualDao.insert(audiovisual)
        val result = audiovisualDao.getAll()

        assertThat(result.size, equalTo(1))
        assertThat(result[0].id, equalTo(audiovisual.id))
        assertThat(result[0].title, equalTo(audiovisual.title))
        assertThat(result[0].genre, equalTo(audiovisual.genre))
        assertThat(result[0].platform, equalTo(audiovisual.platform))
        assertThat(result[0].platformUrl, equalTo(audiovisual.platformUrl))
        assertThat(result[0].startDate, equalTo(audiovisual.startDate))
        assertThat(result[0].deadline, equalTo(audiovisual.deadline))
        assertThat(result[0].check, equalTo(audiovisual.check))
        assertThat(result[0].userId, equalTo(audiovisual.userId))
    }

    @Test
    @Throws(Exception::class)
    fun writeAudiovisualListAndReadInList() {
        val audiovisual: AudiovisualEntity = AudiovisualStubIT.getAudiovisual().toEntity()
        val audiovisual1: AudiovisualEntity = AudiovisualStubIT.getAudiovisual1().toEntity()
        val audiovisuals = listOf(audiovisual, audiovisual1)

        audiovisualDao.insertAll(audiovisuals)
        val result = audiovisualDao.getAll()

        assertThat(result.size, equalTo(2))
        assertThat(result[0].id, equalTo(audiovisual.id))
        assertThat(result[0].title, equalTo(audiovisual.title))
        assertThat(result[0].genre, equalTo(audiovisual.genre))
        assertThat(result[0].platform, equalTo(audiovisual.platform))
        assertThat(result[0].platformUrl, equalTo(audiovisual.platformUrl))
        assertThat(result[0].startDate, equalTo(audiovisual.startDate))
        assertThat(result[0].deadline, equalTo(audiovisual.deadline))
        assertThat(result[0].check, equalTo(audiovisual.check))
        assertThat(result[0].userId, equalTo(audiovisual.userId))
        assertThat(result[1].id, equalTo(audiovisual1.id))
        assertThat(result[1].title, equalTo(audiovisual1.title))
    }

    @Test
    @Throws(Exception::class)
    fun writeAndUpdateAudiovisualAndReadInList() {
        val audiovisual: AudiovisualEntity = AudiovisualStubIT.getAudiovisual().toEntity()

        audiovisualDao.insert(audiovisual)
        val oldAudiovisual = audiovisualDao.getAll()[0]
        val newAudiovisual = getNewAudiovisual(oldAudiovisual.id, oldAudiovisual.userId).toEntity()
        audiovisualDao.update(newAudiovisual)
        val result = audiovisualDao.getAll()

        assertThat(result.size, equalTo(1))
        assertThat(result[0].id, equalTo(oldAudiovisual.id))
        assertThat(result[0].title, equalTo(newAudiovisual.title))
        assertThat(result[0].genre, equalTo(newAudiovisual.genre))
        assertThat(result[0].platform, equalTo(newAudiovisual.platform))
        assertThat(result[0].platformUrl, equalTo(newAudiovisual.platformUrl))
        assertThat(result[0].startDate, equalTo(newAudiovisual.startDate))
        assertThat(result[0].deadline, equalTo(newAudiovisual.deadline))
        assertThat(result[0].check, equalTo(newAudiovisual.check))
        assertThat(result[0].userId, equalTo(oldAudiovisual.userId))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllAudiovisual() {
        val audiovisual: AudiovisualEntity = AudiovisualStubIT.getAudiovisual().toEntity()
        audiovisualDao.insert(audiovisual)

        audiovisualDao.deleteAll()
        val result = audiovisualDao.getAll()

        assertThat(result.size, equalTo(0))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAudiovisualById() {
        val audiovisual: AudiovisualEntity = AudiovisualStubIT.getAudiovisual().toEntity()
        audiovisualDao.insert(audiovisual)

        audiovisual.id?.let { audiovisualDao.deleteById(it) }
        val result = audiovisualDao.getAll()

        assertThat(result.size, equalTo(0))
    }

}