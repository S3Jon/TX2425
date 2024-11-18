package com.example.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AuthorDao {
    @Insert
    fun insertAuthor(author: AuthorEntity)

    @Insert
    fun insertAuthors(authors: List<AuthorEntity>)

    @Query("SELECT * FROM authors")
    fun getAllAuthors(): List<AuthorEntity>

    @Query("SELECT * FROM authors WHERE id = :id")
    fun getAuthorById(id: Long): AuthorEntity

    @Query("SELECT * FROM authors WHERE name = :name")
    fun getAuthorByName(name: String): AuthorEntity?

    @Query("SELECT * FROM authors WHERE country_of_origin = :country")
    fun getByCountry(country: String): List<AuthorEntity>

    @Query("SELECT * FROM authors WHERE date_of_birth = :date")
    fun getByDate(date: String): List<AuthorEntity>

    @Query("SELECT * FROM authors WHERE year_of_birth = :year")
    fun getByYear(year: String): List<AuthorEntity>

    @Query("DELETE FROM authors WHERE id = (SELECT id FROM authors LIMIT 1)")
    fun deleteFirstAuthor()

    @Query("DELETE FROM authors")
    fun emptyAuthorDb()

    @Update
    fun update(author: AuthorEntity)
}