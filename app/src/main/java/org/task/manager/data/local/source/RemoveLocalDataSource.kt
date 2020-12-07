package org.task.manager.data.local.source

import org.task.manager.data.local.dao.AudiovisualDao
import org.task.manager.data.local.dao.BookDao

class RemoveLocalDataSource(private val audiovisualDao: AudiovisualDao,
                            private val bookDao: BookDao) {

    suspend fun deleteAll(){
        audiovisualDao.deleteAll()
        bookDao.deleteAll()
    }

}