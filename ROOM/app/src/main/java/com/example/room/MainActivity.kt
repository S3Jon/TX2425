package com.example.room

import android.content.Intent
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
import com.example.room.Migrations.MIGRATION_2_3
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        database = Room.databaseBuilder(
            application, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).allowMainThreadQueries().addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()

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
        val tvBooks: TextView = findViewById(R.id.tvBookData)
        val btnAuthors: Button = findViewById(R.id.cambiarView)
        val btnPoblar: Button = findViewById(R.id.buttonPoblar)

        btnBooks.setOnClickListener {
            val books = database.booksDao.getAllBooks()
            tvBooks.text = ""
            books.forEach { book ->
                val author = database.authorsDao.getAuthorById(book.idAuthor)
                tvBooks.append("${book.id}, ${book.title}, ${author.name}, ${book.pubDate}, ${book.genre}\n")
            }
        }

        btnAnadir.setOnClickListener {
            val titulo = txtTitulo.text.toString().trim()
            val autor = txtAutor.text.toString().trim()
            val fecha = txtPublish.text.toString().trim()
            if (titulo.isNotEmpty() && autor.isNotEmpty() && fecha.isNotEmpty()) {
                val author = database.authorsDao.getAuthorByName(autor)
                if (author != null) {
                    val query = BookEntity(
                        title = titulo,
                        idAuthor = author.id,
                        pubDate = fecha,
                        genre = "lol"
                    )
                    database.booksDao.insertBook(query)
                } else {
                    Toast.makeText(this, "Autor no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnActualizar.setOnClickListener {
            val titulo = txtTitulo.text.toString().trim()
            val autor = txtAutor.text.toString().trim()
            val fecha = txtPublish.text.toString().trim()
            if (titulo.isNotEmpty() && autor.isNotEmpty() && fecha.isNotEmpty()) {
                val author = database.authorsDao.getAuthorByName(autor)
                if (author != null) {
                    val query = BookEntity(
                        title = titulo,
                        idAuthor = author.id,
                        pubDate = fecha,
                        genre = "lol"
                    )
                    database.booksDao.update(query)
                } else {
                    Toast.makeText(this, "Autor no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnBorrarPrimer.setOnClickListener {
            database.booksDao.deleteFirstBook()
        }

        btnBorrarTodo.setOnClickListener {
            database.booksDao.emptyBookDb()
            database.authorsDao.emptyAuthorDb()
            Toast.makeText(this, "Base de datos borrada", Toast.LENGTH_SHORT).show()
            tvBooks.text = ""
        }

        btnLibroPorAutor.setOnClickListener {
            val autor = txtAutor.text.toString().trim()
            if (autor.isNotEmpty()) {
                val author = database.authorsDao.getAuthorByName(autor)
                if (author != null) {
                    val books = database.booksDao.getByAuthor(author.id)
                    tvBooks.text = ""
                    if (books.isEmpty()) {
                        Toast.makeText(this, "No se encontraron libros", Toast.LENGTH_SHORT).show()
                    } else {
                        books.forEach { book ->
                            tvBooks.append("${book.id}, ${book.title}, ${author.name}, ${book.pubDate}, ${book.genre}\n")
                        }
                    }
                } else {
                    Toast.makeText(this, "Autor no encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingrese un autor", Toast.LENGTH_SHORT).show()
            }
        }

        btnMostrarTodo.setOnClickListener {
            val books = database.booksDao.getAllBooks()
            tvBooks.text = ""
            books.forEach { book ->
                val author = database.authorsDao.getAuthorById(book.idAuthor)
                tvBooks.append("${book.id}, ${book.title}, ${author.name}, ${book.pubDate}, ${book.genre}\n")
            }
        }

        btnAuthors.setOnClickListener {
           intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        btnPoblar.setOnClickListener {
            var mensaje = ""
            if (database.authorsDao.getAllAuthors().isNotEmpty()) {
                mensaje = "Authors ya tiene datos."
            } else {
                saveAuthors()
                mensaje = "Authors poblado."
            }
            if (database.booksDao.getAllBooks().isNotEmpty()) {
                mensaje = mensaje.plus(" Books ya tiene datos.")
            } else {
                saveBooks()
                mensaje = mensaje.plus(" Books poblado.")
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }


    }

    private fun saveBooks() {
        val book1 = BookEntity(title = "Mobby Dick", idAuthor = 1, pubDate = "1851", genre = "Lol")
        val book2 = BookEntity(title = "A tale of two cities", idAuthor = 2, pubDate = "1859", genre = "Lol")
        val book3 = BookEntity(title = "The Metamorphosis", idAuthor = 3, pubDate = "1915", genre = "Lol")
        val book4 = BookEntity(title = "Crime and Punishment", idAuthor = 4, pubDate = "1866", genre = "Lol")
        val book5 = BookEntity(title = "Don Quixote", idAuthor = 5, pubDate = "1605", genre = "Lol")
        database.booksDao.insertBook(book1)
        database.booksDao.insertBook(book2)
        database.booksDao.insertBook(book3)
        database.booksDao.insertBook(book4)
        database.booksDao.insertBook(book5)
    }

    private fun saveAuthors() {
        val author1 = AuthorEntity(name = "Herman Melville", countryOfOrigin = "USA", dateOfBirth = "1/8", yearOfBirth = "1819", mainGenre = "Lol")
        val author2 = AuthorEntity(name = "Charles Dickens", countryOfOrigin = "UK", dateOfBirth = "7/2", yearOfBirth = "1812", mainGenre = "Lol")
        val author3 = AuthorEntity(name = "Franz Kafka", countryOfOrigin = "Czech Republic", dateOfBirth = "3/7", yearOfBirth = "1883", mainGenre = "Lol")
        val author4 = AuthorEntity(name = "Fyodor Dostoevsky", countryOfOrigin = "Russia", dateOfBirth = "11/11", yearOfBirth = "1821", mainGenre = "Lol")
        val author5 = AuthorEntity(name = "Miguel de Cervantes", countryOfOrigin = "Spain", dateOfBirth = "9/29", yearOfBirth = "1547", mainGenre = "Lol")
        database.authorsDao.insertAuthor(author1)
        database.authorsDao.insertAuthor(author2)
        database.authorsDao.insertAuthor(author3)
        database.authorsDao.insertAuthor(author4)
        database.authorsDao.insertAuthor(author5)
    }
}