package com.example.room

import androidx.room.ColumnInfo

data class BookWithAuthor(
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "idAuthor")
    val idAuthor: Long,

    @ColumnInfo(name = "pub_date")
    val pubDate: String,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "authorName")
    val authorName: String
)