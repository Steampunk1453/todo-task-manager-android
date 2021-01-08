package org.task.manager.instrumented.integration.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.task.manager.data.local.AppDatabase
import org.task.manager.data.local.dao.PlatformDao
import org.task.manager.data.local.model.toEntity
import org.task.manager.instrumented.integration.stub.PlatformStubIT
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PlatformDaoTest {

    private lateinit var platformDao: PlatformDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        platformDao = db.platformDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writePlatformListAndReadInList() = runBlocking {
        val platform = PlatformStubIT.getPlatform().toEntity()
        val platform1 = PlatformStubIT.getPlatform1().toEntity()
        val platforms = listOf(platform, platform1)

        platformDao.insertAll(platforms)
        val result = platformDao.getAll()

        assertThat(result.size, equalTo(2))
        assertThat(result[0].id, equalTo(platform.id))
        assertThat(result[0].name, equalTo(platform.name))
        assertThat(result[0].url, equalTo(platform.url))
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteAllPlatforms() = runBlocking {
        val platform = PlatformStubIT.getPlatform().toEntity()
        val platform1 = PlatformStubIT.getPlatform1().toEntity()
        val platforms = listOf(platform, platform1)
        platformDao.insertAll(platforms)

        platformDao.deleteAll()
        val result = platformDao.getAll()

        assertThat(result.size, equalTo(0))
    }

}