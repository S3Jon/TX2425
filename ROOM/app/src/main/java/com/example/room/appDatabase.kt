package com.example.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class, AuthorEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val booksDao: BooksDao
    abstract val authorsDao: AuthorDao


    companion object {
        const val DATABASE_NAME = "db-adibide"
    }
}