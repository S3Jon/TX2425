package com.example.room

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.room.Room
import com.example.room.Migrations.MIGRATION_1_2
import org.w3c.dom.Text

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
        //saveBooks()
        val btnBooks: Button = findViewById(R.id.btnBooks)
        val btnAnadir: Button = findViewById(R.id.buttonAnadir)
        val btnActualizar: Button = findViewById(R.id.btnActualizar)
        val btnBorrarPrimer: Button = findViewById(R.id.button3)
        val btnBorrarTodo: Button = findViewById(R.id.btn4)
        val btnLibroPorAutor: Button = findViewById(R.id.button5)
        val btnMostrarTodo: Button = findViewById(R.id.btn6)
        val txtAutor: TextView = findViewById(R.id.etAuthorName)
        val txtTitulo: TextView = findViewById(R.id.etBookTitle)
        val txtPublish: TextView = findViewById(R.id.etPublicationDate)

        // Recuperar el EditText para el título del libro
        val tvBooks: TextView = findViewById(R.id.tvBookData)
        btnBooks.setOnClickListener {
            val books = database.booksDao.getAllBooks()
            tvBooks.setText("")
            books.forEach { book ->
                tvBooks.append("${book.id}, ${book.title}, ${book.author}, ${book.pubDate}, ${book.genre}\n")
            }
        }
        btnAnadir.setOnClickListener {
            val titulo = txtTitulo.text.toString().trim()
            val autor = txtAutor.text.toString().trim()
            val fecha = txtPublish.text.toString().trim()
            if (fecha.isNotEmpty() && autor.isNotEmpty() && fecha.isNotEmpty()) {
                val query = BookEntity(
                    title = "$titulo",
                    author = "$autor",
                    pubDate = "$fecha",
                    genre = "lol"
                )
                Toast.makeText(this, "Libro añadido", Toast.LENGTH_SHORT).show()
                database.booksDao.insertBook(query)
                btnMostrarTodo.performClick()
            }
            else{
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        btnActualizar.setOnClickListener {
            val titulo = txtTitulo.text.toString().trim()
            val autor = txtAutor.text.toString().trim()
            val fecha = txtPublish.text.toString().trim()
            if (fecha.isNotEmpty() && autor.isNotEmpty() && fecha.isNotEmpty()) {
                val query = BookEntity(
                    title = "$titulo",
                    author = "$autor",
                    pubDate = "$fecha",
                    genre = "lol"
                )
                database.booksDao.update(query)
            }
        }
        btnBorrarPrimer.setOnClickListener {
            database.booksDao.deleteFirstBook()
        }
        btnBorrarTodo.setOnClickListener {
            database.booksDao.emptyBookDb()
        }
        btnLibroPorAutor.setOnClickListener {
            val autor = txtAutor.text.toString().trim()
            if (autor.isNotEmpty()) {
                val books = database.booksDao.getByAuthor(autor)
                tvBooks.setText("")
                if (books.isEmpty()) {
                    Toast.makeText(this, "No se encontraron libros", Toast.LENGTH_SHORT).show()
                }
                else {
                    books.forEach { book ->
                        tvBooks.append("${book.id}, ${book.title}, ${book.author}, ${book.pubDate}, ${book.genre}\n")
                    }
                }
            }
            else {
                Toast.makeText(this, "Ingrese un autor", Toast.LENGTH_SHORT).show()
            }
        }
        btnMostrarTodo.setOnClickListener {
            val books = database.booksDao.getAllBooks()
            tvBooks.setText("")
            books.forEach { book ->
                tvBooks.append("${book.id}, ${book.title}, ${book.author}, ${book.pubDate}, ${book.genre}\n")
            }
        }
    }

    private fun saveBooks() {
        val book1 = BookEntity(title = "Mobby Dack", author = "Herman Melville", pubDate = "1851", genre = "Lol")
        val book2 = BookEntity(title = "Mobby Deck 2", author = "Hermen Melville", pubDate = "1851", genre = "Lol")
        val book3 = BookEntity(title = "Mobby Dick 3", author = "Hermin Melville", pubDate = "1851", genre = "Lol")
        val book4 = BookEntity (title = "Mobby Dock 4", author = "Hermon Melville", pubDate = "1851", genre = "Lol")
        val book5 = BookEntity(title = "Mobby Duck 5", author = "Hermun Melville", pubDate = "1851", genre = "Lol")
        database.booksDao.insertBook(book1)
        database.booksDao.insertBook(book2)
        database.booksDao.insertBook(book3)
        database.booksDao.insertBook(book4)
        database.booksDao.insertBook(book5)
    }
}