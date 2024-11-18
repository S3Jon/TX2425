package com.example.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "country_of_origin")
    var countryOfOrigin: String,

    @ColumnInfo(name = "date_of_birth")
    var dateOfBirth: String = "02-06",

    @ColumnInfo(name = "year_of_birth")
    var yearOfBirth: String = "1989",

    @ColumnInfo(name = "main_genre")
    var mainGenre: String = ""
)