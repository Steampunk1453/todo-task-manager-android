package org.task.manager.data.local.source

import org.task.manager.data.local.dao.AudiovisualDao
import org.task.manager.data.local.dao.PlatformDao
import org.task.manager.data.local.dao.TitleDao
import org.task.manager.data.local.model.AudiovisualEntity
import org.task.manager.data.local.model.PlatformEntity
import org.task.manager.data.local.model.TitleEntity

class AudiovisualLocalDataSource(private val audiovisualDao: AudiovisualDao,
                                 private val titleDao: TitleDao,
                                 private val platformDao: PlatformDao
) {

    fun findAll(): List<AudiovisualEntity> = audiovisualDao.getAll()

    suspend fun saveAll(audiovisuals: List<AudiovisualEntity>) = audiovisualDao.insertAll(audiovisuals)

    suspend fun save(audiovisual: AudiovisualEntity) = audiovisualDao.insert(audiovisual)

    suspend fun update(audiovisual: AudiovisualEntity) = audiovisualDao.update(audiovisual)

    suspend fun delete(id: Long) = audiovisualDao.deleteById(id)

    fun findAllTitles(): List<TitleEntity> = titleDao.getAll()

    suspend fun saveAllTitles(titles: List<TitleEntity>) = titleDao.insertAll(titles)

    fun findAllPlatforms(): List<PlatformEntity> = platformDao.getAll()

    suspend fun saveAllPlatforms(platforms: List<PlatformEntity>) = platformDao.insertAll(platforms)

}