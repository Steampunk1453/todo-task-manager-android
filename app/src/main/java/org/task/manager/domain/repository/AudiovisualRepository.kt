package org.task.manager.domain.repository

import org.task.manager.domain.Result
import org.task.manager.domain.model.Audiovisual
import org.task.manager.domain.model.Platform
import org.task.manager.domain.model.Title

interface AudiovisualRepository {
    suspend fun save(audiovisual: Audiovisual): Result<Audiovisual>
    suspend fun update(audiovisual: Audiovisual): Result<Audiovisual>
    suspend fun getAll(): Result<List<Audiovisual>>
    suspend fun remove(id: Long)
    suspend fun getAllTitles(): Result<List<Title>>
    suspend fun getAllPlatforms(): Result<List<Platform>>
}
