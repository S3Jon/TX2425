package com.example.room

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.room.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.btnCambiarView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnMostratAutores.setOnClickListener {
            val autores = database.authorsDao.getAllAuthors()
            binding.tvAuthorData.text = ""
            autores.forEach { autor ->
                binding.tvAuthorData.append("${autor.id}, ${autor.name}, ${autor.countryOfOrigin}, ${autor.dateOfBirth}, ${autor.yearOfBirth}, ${autor.mainGenre}\n")
            }
        }
    }
}