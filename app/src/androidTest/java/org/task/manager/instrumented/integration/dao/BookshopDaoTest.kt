package org.task.manager.instrumented.integration.dao

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
import org.task.manager.data.local.dao.BookshopDao
import org.task.manager.data.local.model.toEntity
import org.task.manager.instrumented.integration.stub.BookshopStubIT
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BookshopDaoTest {

    private lateinit var bookshopDao: BookshopDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        bookshopDao = db.bookshopDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeBookshopListAndReadInList() {
        val bookshop = BookshopStubIT.getBookshop().toEntity()
        val bookshop1 = BookshopStubIT.getBookshop1().toEntity()
        val bookshops = listOf(bookshop, bookshop1)

        bookshopDao.insertAll(bookshops)
        val result = bookshopDao.getAll()

        assertThat(result.size, equalTo(2))
        assertThat(result[0].id, equalTo(bookshop.id))
        assertThat(result[0].name, equalTo(bookshop.name))
        assertThat(result[0].url, equalTo(bookshop.url))
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteAllBookshops() {
        val bookshop = BookshopStubIT.getBookshop().toEntity()
        val bookshop1 = BookshopStubIT.getBookshop1().toEntity()
        val bookshops = listOf(bookshop, bookshop1)
        bookshopDao.insertAll(bookshops)

        bookshopDao.deleteAll()
        val result = bookshopDao.getAll()

        assertThat(result.size, equalTo(0))
    }

}