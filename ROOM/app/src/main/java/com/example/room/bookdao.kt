package com.example.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BooksDao {
    @Insert
    fun insertBook(book: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM books WHERE title = :title")
    fun getByTitle(title: String): List<BookEntity>

    @Query("SELECT * FROM books WHERE idAuthor = :authorId")
    fun getByAuthor(authorId: Long): List<BookEntity>

    @Query("DELETE FROM books WHERE id = (SELECT id FROM books LIMIT 1)")
    fun deleteFirstBook()

    @Query("DELETE FROM books")
    fun emptyBookDb()

    @Query("SELECT EXISTS(SELECT 1 FROM books WHERE title = :title AND idAuthor = :idauthor)")
    fun getBookByTitleAndAuthor(title: String, idauthor: Long): Boolean

    @Update
    fun update(book: BookEntity)
}