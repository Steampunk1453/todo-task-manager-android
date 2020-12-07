package org.task.manager.data.local.source

import org.task.manager.data.local.dao.GenreDao
import org.task.manager.data.local.model.GenreEntity

class GenreLocalDataSource(private val genreDao: GenreDao) {

    fun findAll(): List<GenreEntity> = genreDao.getAll()

    suspend fun saveAll(genres: List<GenreEntity>) = genreDao.insertAll(genres)

}