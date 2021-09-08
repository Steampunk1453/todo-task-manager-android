package org.task.manager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.task.manager.data.local.dao.*
import org.task.manager.data.local.model.*

@Database(
    entities = [AudiovisualEntity::class, TitleEntity::class, PlatformEntity::class, BookEntity::class,
        EditorialEntity::class, BookshopEntity::class, GenreEntity::class], version = 2
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

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE TITLE")
                database.execSQL("CREATE TABLE IF NOT EXISTS `TitleInfo` ("
                        + "`id` TEXT NOT NULL, "
                        + "`title` TEXT,"
                        + "`rank` NUMBER,"
                        + "`type` TEXT,"
                        + "`genres` TEXT,"
                        + "`platform` TEXT,"
                        + "`website` TEXT,"
                        + "PRIMARY KEY(`id`)"
                        +")"
                )
            }
        }
    }

}