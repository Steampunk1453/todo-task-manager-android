package org.task.manager.data.local.source

import org.task.manager.data.local.dao.BookDao
import org.task.manager.data.local.dao.BookshopDao
import org.task.manager.data.local.dao.EditorialDao
import org.task.manager.data.local.model.BookEntity
import org.task.manager.data.local.model.BookshopEntity
import org.task.manager.data.local.model.EditorialEntity

class BookLocalDataSource(
    private val dao: BookDao,
    private val editorialDao: EditorialDao,
    private val bookshopDao: BookshopDao
) {

    fun findAll(): List<BookEntity> = dao.getAll()

    fun saveAll(books: List<BookEntity>) = dao.insertAll(books)

    fun save(book: BookEntity) = dao.insert(book)

    fun update(book: BookEntity) = dao.update(book)

    fun delete(id: Long) = dao.deleteById(id)

    fun findAllEditorials(): List<EditorialEntity> = editorialDao.getAll()

    fun saveAllEditorials(editorials: List<EditorialEntity>) =
        editorialDao.insertAll(editorials)

    fun findAllBookshops(): List<BookshopEntity> = bookshopDao.getAll()

    fun saveAllBookshops(bookshops: List<BookshopEntity>) = bookshopDao.insertAll(bookshops)

}