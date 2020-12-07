package org.task.manager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.task.manager.data.local.dao.AudiovisualDao
import org.task.manager.data.local.dao.BookDao
import org.task.manager.data.local.dao.BookshopDao
import org.task.manager.data.local.dao.EditorialDao
import org.task.manager.data.local.dao.GenreDao
import org.task.manager.data.local.dao.PlatformDao
import org.task.manager.data.local.dao.TitleDao
import org.task.manager.data.local.model.AudiovisualEntity
import org.task.manager.data.local.model.BookEntity
import org.task.manager.data.local.model.BookshopEntity
import org.task.manager.data.local.model.EditorialEntity
import org.task.manager.data.local.model.GenreEntity
import org.task.manager.data.local.model.PlatformEntity
import org.task.manager.data.local.model.TitleEntity

@Database(
    entities = [AudiovisualEntity::class, TitleEntity::class, PlatformEntity::class, BookEntity::class,
        EditorialEntity::class, BookshopEntity::class, GenreEntity::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun audiovisualDao(): AudiovisualDao
    abstract fun titleDao(): TitleDao
    abstract fun platformDao(): PlatformDao
    abstract fun bookDao(): BookDao
    abstract fun editorialDao(): EditorialDao
    abstract fun bookshopDao(): BookshopDao
    abstract fun genreDao(): GenreDao

    companion object {
        const val DATABASE_NAME = "todo_task_manager.db"
    }

}