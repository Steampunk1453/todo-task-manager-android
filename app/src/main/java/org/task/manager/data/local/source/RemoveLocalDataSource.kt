package org.task.manager.data.local.source

import org.task.manager.data.local.dao.AudiovisualDao
import org.task.manager.data.local.dao.BookDao
import org.task.manager.data.local.dao.BookshopDao
import org.task.manager.data.local.dao.EditorialDao
import org.task.manager.data.local.dao.GenreDao
import org.task.manager.data.local.dao.PlatformDao
import org.task.manager.data.local.dao.TitleDao

class RemoveLocalDataSource(private val audiovisualDao: AudiovisualDao,
                            private val bookDao: BookDao,
                            private val titleDao: TitleDao,
                            private val platformDao: PlatformDao,
                            private val editorialDao: EditorialDao,
                            private val bookshopDao: BookshopDao,
                            private val genreDao: GenreDao) {

    suspend fun deleteAll(){
        audiovisualDao.deleteAll()
        bookDao.deleteAll()
        titleDao.deleteAll()
        platformDao.deleteAll()
        editorialDao.deleteAll()
        bookshopDao.deleteAll()
        genreDao.deleteAll()
    }

}