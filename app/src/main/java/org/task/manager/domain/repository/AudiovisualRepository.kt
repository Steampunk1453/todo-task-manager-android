package org.task.manager.domain.repository

import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Platform
import org.task.manager.domain.model.Title

interface AudiovisualRepository {
    suspend fun save(audiovisual: Audiovisual): Audiovisual
    suspend fun update(audiovisual: Audiovisual): Audiovisual
    suspend fun getAll(): List<Audiovisual>
    suspend fun remove(id: Long)
    suspend fun getAllTitles(): List<Title>
    suspend fun getAllPlatforms(): List<Platform>
}
