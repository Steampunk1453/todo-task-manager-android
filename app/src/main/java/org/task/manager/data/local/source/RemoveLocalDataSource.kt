package org.task.manager.data.local.source

import org.task.manager.data.local.dao.AudiovisualDao

class RemoveLocalDataSource(private val dao: AudiovisualDao) {

    suspend fun deleteAll() = dao.deleteAll()

}