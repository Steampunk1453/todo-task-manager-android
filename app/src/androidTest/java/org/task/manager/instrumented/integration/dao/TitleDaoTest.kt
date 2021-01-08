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
import org.task.manager.data.local.dao.TitleDao
import org.task.manager.data.local.model.toEntity
import org.task.manager.instrumented.integration.stub.TitleStubIT
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TitleDaoTest {

    private lateinit var titleDao: TitleDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        titleDao = db.titleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeTitleListAndReadInList() = runBlocking {
        val title = TitleStubIT.getTitle().toEntity()
        val title1 = TitleStubIT.getTitle1().toEntity()
        val titles = listOf(title, title1)

        titleDao.insertAll(titles)
        val result = titleDao.getAll()

        assertThat(result.size, equalTo(2))
        assertThat(result[0].id, equalTo(title.id))
        assertThat(result[0].name, equalTo(title.name))
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteAllTitles() = runBlocking {
        val title = TitleStubIT.getTitle().toEntity()
        val title1 = TitleStubIT.getTitle1().toEntity()
        val titles = listOf(title, title1)
        titleDao.insertAll(titles)

        titleDao.deleteAll()
        val result = titleDao.getAll()

        assertThat(result.size, equalTo(0))
    }

}