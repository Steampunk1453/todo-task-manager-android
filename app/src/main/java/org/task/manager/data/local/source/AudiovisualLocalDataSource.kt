package org.task.manager.data.local.source

import org.task.manager.data.local.dao.AudiovisualDao
import org.task.manager.data.local.model.AudiovisualEntity

class AudiovisualLocalDataSource(private val dao: AudiovisualDao) {

    fun findAll(): List<AudiovisualEntity> = dao.getAll()

    suspend fun saveAll(audiovisuals: List<AudiovisualEntity>) = dao.insertAll(audiovisuals)

    suspend fun save(audiovisual: AudiovisualEntity) = dao.insert(audiovisual)

    suspend fun update(audiovisual: AudiovisualEntity) = dao.update(audiovisual)

    suspend fun delete(id: Long) = dao.deleteById(id)

}