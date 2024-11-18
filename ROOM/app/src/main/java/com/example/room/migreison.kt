package com.example.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE books ADD COLUMN genre TEXT NOT NULL DEFAULT 0")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE authors (id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL, country_of_origin TEXT NOT NULL, date_of_birth TEXT NOT NULL, year_of_birth TEXT NOT NULL, main_genre TEXT NOT NULL)")
            db.execSQL("DELETE FROM books")
            db.execSQL("ALTER TABLE books DROP COLUMN author")
            db.execSQL("ALTER TABLE books ADD COLUMN idAuthor INTEGER NOT NULL DEFAULT 0 REFERENCES authors(id)")
        }
    }
}