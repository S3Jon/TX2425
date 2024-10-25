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

    @Query("SELECT * FROM books WHERE title =:title")
    fun getByTitle(title:String): List<BookEntity>
    @Update
    fun update(book: BookEntity)
}