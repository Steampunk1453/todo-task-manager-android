package org.task.manager.domain.repository

import org.task.manager.domain.model.Audiovisual

interface AudiovisualRepository {
    suspend fun getAll(): List<Audiovisual>
}
