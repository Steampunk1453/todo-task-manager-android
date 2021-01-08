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
import org.task.manager.data.local.dao.EditorialDao
import org.task.manager.data.local.model.toEntity
import org.task.manager.instrumented.integration.stub.EditorialStubIT
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EditorialDaoTest {

    private lateinit var editorialDao: EditorialDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        editorialDao = db.editorialDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeEditorialListAndReadInList() = runBlocking {
        val editorial = EditorialStubIT.getEditorial().toEntity()
        val editorial1 = EditorialStubIT.getEditorial1().toEntity()
        val editorials = listOf(editorial, editorial1)

        editorialDao.insertAll(editorials)
        val result = editorialDao.getAll()

        assertThat(result.size, equalTo(2))
        assertThat(result[0].id, equalTo(editorial.id))
        assertThat(result[0].name, equalTo(editorial.name))
        assertThat(result[0].url, equalTo(editorial.url))
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteAllEditorials() = runBlocking {
        val editorial = EditorialStubIT.getEditorial().toEntity()
        val editorial1 = EditorialStubIT.getEditorial1().toEntity()
        val editorials = listOf(editorial, editorial1)
        editorialDao.insertAll(editorials)

        editorialDao.deleteAll()
        val result = editorialDao.getAll()

        assertThat(result.size, equalTo(0))
    }

}