package com.example.room

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.room.Room
import com.example.room.Migrations.MIGRATION_1_2

class MainActivity: AppCompatActivity() {
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets (WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        database = Room.databaseBuilder(
            application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries().addMigrations(MIGRATION_1_2)
            .build()
        saveBooks()
        val btnBooks: Button = findViewById(R.id.btnBooks)
        // Recuperar el EditText para el título del libro
        val tvBooks: TextView = findViewById(R.id.tvBookData)
        btnBooks.setOnClickListener {
            val books = database.booksDao.getAllBooks()
            books.forEach { book ->
                tvBooks.append("${book.id}, ${book.title}, ${book.author}, ${book.pubDate}\n")
            }
        }
    }

    private fun saveBooks() {
        val book1 = BookEntity(title = "Mobby Dick", author = "Herman Melville", pubDate = "1851")
        val book2 = BookEntity(title = "Mobby Dick 2", author = "Herman Melville", pubDate = "1851")
        val book3 = BookEntity(title = "Mobby Dick 3", author = "Herman Melville", pubDate = "1851")
        val book4 = BookEntity (title = "Mobby Dick 4", author = "Herman Melville", pubDate = "1851")
        val book5 = BookEntity(title = "Mobby Dick 5", author = "Herman Melville", pubDate = "1851")
        database.booksDao.insertBook(book1)
        database.booksDao.insertBook(book2)
        database.booksDao.insertBook(book3)
        database.booksDao.insertBook(book4)
        database.booksDao.insertBook(book5)
    }
}