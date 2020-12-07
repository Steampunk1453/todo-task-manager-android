package org.task.manager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.task.manager.data.local.dao.AudiovisualDao
import org.task.manager.data.local.dao.BookDao
import org.task.manager.data.local.model.AudiovisualEntity
import org.task.manager.data.local.model.BookEntity

@Database(entities = [AudiovisualEntity::class, BookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun audiovisualDao(): AudiovisualDao
    abstract fun bookDao(): BookDao

    companion object {
        const val DATABASE_NAME = "todo_task_manager.db"
    }

}