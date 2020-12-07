package org.task.manager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.task.manager.data.local.dao.AudiovisualDao
import org.task.manager.data.local.model.AudiovisualEntity

@Database(entities = [AudiovisualEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun audiovisualDao(): AudiovisualDao

    companion object {
        const val DATABASE_NAME = "todo_task_manager.db"
    }

}