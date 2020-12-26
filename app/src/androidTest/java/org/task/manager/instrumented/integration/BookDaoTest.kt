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
import org.task.manager.data.local.dao.BookDao
import org.task.manager.data.local.model.toEntity
import org.task.manager.instrumented.integration.stub.BookStubIT
import org.task.manager.instrumented.integration.stub.BookStubIT.Companion.getNewBook
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BookDaoTest {

    private lateinit var bookDao: BookDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        bookDao = db.bookDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeBookAndReadInList() {
        val book = BookStubIT.getBook().toEntity()

        bookDao.insert(book)
        val result = bookDao.getAll()

        assertThat(result.size, equalTo(1))
        assertThat(result[0].id, equalTo(book.id))
        assertThat(result[0].title, equalTo(book.title))
        assertThat(result[0].author, equalTo(book.author))
        assertThat(result[0].genre, equalTo(book.genre))
        assertThat(result[0].editorial, equalTo(book.editorial))
        assertThat(result[0].editorialUrl, equalTo(book.editorialUrl))
        assertThat(result[0].bookshop, equalTo(book.bookshop))
        assertThat(result[0].bookshopUrl, equalTo(book.bookshopUrl))
        assertThat(result[0].startDate, equalTo(book.startDate))
        assertThat(result[0].deadline, equalTo(book.deadline))
        assertThat(result[0].check, equalTo(book.check))
        assertThat(result[0].userId, equalTo(book.userId))
    }

    @Test
    @Throws(Exception::class)
    fun writeBookListAndReadInList() {
        val book = BookStubIT.getBook().toEntity()
        val book1 = BookStubIT.getBook1().toEntity()
        val books = listOf(book, book1)

        bookDao.insertAll(books)
        val result = bookDao.getAll()

        assertThat(result.size, equalTo(2))
        assertThat(result[0].id, equalTo(book.id))
        assertThat(result[0].title, equalTo(book.title))
        assertThat(result[0].author, equalTo(book.author))
        assertThat(result[0].genre, equalTo(book.genre))
        assertThat(result[0].editorial, equalTo(book.editorial))
        assertThat(result[0].editorialUrl, equalTo(book.editorialUrl))
        assertThat(result[0].bookshop, equalTo(book.bookshop))
        assertThat(result[0].bookshopUrl, equalTo(book.bookshopUrl))
        assertThat(result[0].startDate, equalTo(book.startDate))
        assertThat(result[0].deadline, equalTo(book.deadline))
        assertThat(result[0].check, equalTo(book.check))
        assertThat(result[0].userId, equalTo(book.userId))
        assertThat(result[1].id, equalTo(book1.id))
        assertThat(result[1].title, equalTo(book1.title))
    }

    @Test
    @Throws(Exception::class)
    fun writeAndUpdateBookAndReadInList() {
        val book = BookStubIT.getBook().toEntity()

        bookDao.insert(book)
        val oldBook = bookDao.getAll()[0]
        val newBook = getNewBook(oldBook.id, oldBook.userId).toEntity()
        bookDao.update(newBook)
        val result = bookDao.getAll()

        assertThat(result.size, equalTo(1))
        assertThat(result[0].id, equalTo(oldBook.id))
        assertThat(result[0].title, equalTo(newBook.title))
        assertThat(result[0].author, equalTo(newBook.author))
        assertThat(result[0].genre, equalTo(newBook.genre))
        assertThat(result[0].editorial, equalTo(newBook.editorial))
        assertThat(result[0].editorialUrl, equalTo(newBook.editorialUrl))
        assertThat(result[0].bookshop, equalTo(newBook.bookshop))
        assertThat(result[0].bookshopUrl, equalTo(newBook.bookshopUrl))
        assertThat(result[0].startDate, equalTo(newBook.startDate))
        assertThat(result[0].deadline, equalTo(newBook.deadline))
        assertThat(result[0].check, equalTo(newBook.check))
        assertThat(result[0].userId, equalTo(oldBook.userId))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllBooks() {
        val book = BookStubIT.getBook().toEntity()
        bookDao.insert(book)

        bookDao.deleteAll()
        val result = bookDao.getAll()

        assertThat(result.size, equalTo(0))
    }

    @Test
    @Throws(Exception::class)
    fun deleteBookById() {
        val book = BookStubIT.getBook().toEntity()
        bookDao.insert(book)

        book.id?.let { bookDao.deleteById(it) }
        val result = bookDao.getAll()

        assertThat(result.size, equalTo(0))
    }

}