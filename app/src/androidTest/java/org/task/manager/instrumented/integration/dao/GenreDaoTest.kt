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
import org.task.manager.data.local.dao.GenreDao
import org.task.manager.data.local.model.toEntity
import org.task.manager.instrumented.integration.stub.GenreStubIT
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GenreDaoTest {

    private lateinit var genreDao: GenreDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        genreDao = db.genreDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeGenreListAndReadInList() = runBlocking {
        val genre = GenreStubIT.getGenre().toEntity()
        val genre1 = GenreStubIT.getGenre1().toEntity()
        val genres = listOf(genre, genre1)

        genreDao.insertAll(genres)
        val result = genreDao.getAll()

        assertThat(result.size, equalTo(2))
        assertThat(result[0].id, equalTo(genre.id))
        assertThat(result[0].name, equalTo(genre.name))
        assertThat(result[0].isLiterary, equalTo(genre.isLiterary))
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteAllGenres() = runBlocking {
        val editorial = GenreStubIT.getGenre().toEntity()
        val editorial1 = GenreStubIT.getGenre1().toEntity()
        val editorials = listOf(editorial, editorial1)
        genreDao.insertAll(editorials)

        genreDao.deleteAll()
        val result = genreDao.getAll()

        assertThat(result.size, equalTo(0))
    }

}