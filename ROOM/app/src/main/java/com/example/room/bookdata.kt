package com.example.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "books", foreignKeys = [ForeignKey(entity = AuthorEntity::class, parentColumns = ["id"], childColumns = ["idAuthor"])])
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "title")
    @NonNull
    var title: String,

    @ColumnInfo(name = "idAuthor")
    @NonNull
    var idAuthor: Long,

    @ColumnInfo(name = "pub_date")
    @NonNull
    var pubDate: String,

    @ColumnInfo(name = "genre")
    var genre: String = ""
)