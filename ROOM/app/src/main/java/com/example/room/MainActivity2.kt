package com.example.room

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        binding.buttonAnadir.setOnClickListener {
            val nombre = binding.etAuthorName.text.toString().trim()
            val pais = binding.etAuthorCountry.text.toString().trim()
            val fecha = binding.etBirthYear.text.toString().trim()

            if (fecha.isNotEmpty() && nombre.isNotEmpty() && pais.isNotEmpty()) {
                val regex = Regex("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$")
                if (regex.matches(fecha)) {
                    val dia = fecha.substringBefore("/")
                    val mes = fecha.substringAfter("/").substringBefore("/")
                    val anio = fecha.substringAfterLast("/")
                    val autor = AuthorEntity(
                        name = nombre,
                        countryOfOrigin = pais,
                        dateOfBirth = dia + "/" + mes,
                        yearOfBirth = anio,
                        mainGenre = "Lol"
                    )
                    database.authorsDao.insertAuthor(autor)
                    Toast.makeText(this, "Autor añadido", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Formato de fecha incorrecto (DD/MM/YYYY)", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnActualizar.setOnClickListener {
            val nombre = binding.etAuthorName.text.toString().trim()
            val pais = binding.etAuthorCountry.text.toString().trim()
            val fecha = binding.etBirthYear.text.toString().trim()
            if (fecha.isNotEmpty() && nombre.isNotEmpty() && pais.isNotEmpty()) {
                val regex = Regex("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$")

                if (regex.matches(fecha)) {
                    val dia = fecha.substringBefore("/")
                    val mes = fecha.substringAfter("/").substringBefore("/")
                    val anio = fecha.substringAfterLast("/")
                    val autor = AuthorEntity(
                        name = nombre,
                        countryOfOrigin = pais,
                        dateOfBirth = dia + "/" + mes,
                        yearOfBirth = anio,
                        mainGenre = "Lol"
                    )
                    database.authorsDao.update(autor)
                    Toast.makeText(this, "Autor actualizado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "Formato de fecha incorrecto (DD/MM/YYYY)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBuscarPorNombre.setOnClickListener {
            val nombre = binding.etAuthorName.text.toString().trim()
            if (nombre.isNotEmpty()) {
                val autor = database.authorsDao.getAuthorsByName(nombre)
                binding.tvAuthorData.text = ""
                autor.forEach { autor ->
                    binding.tvAuthorData.append("${autor.id}, ${autor.name}, ${autor.countryOfOrigin}, ${autor.dateOfBirth}, ${autor.yearOfBirth}, ${autor.mainGenre}\n")
                }
                if (autor.isEmpty()) {
                    binding.tvAuthorData.text = "No se encontraron autores"
                }
            }
            else {
                Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBuscarPorPais.setOnClickListener {
            val pais = binding.etAuthorCountry.text.toString().trim()
            if (pais.isNotEmpty()) {
                val autores = database.authorsDao.getByCountry(pais)
                binding.tvAuthorData.text = ""
                if (autores.isEmpty()) {
                    binding.tvAuthorData.text = "No se encontraron autores"
                }
                else {
                    autores.forEach { autor ->
                        binding.tvAuthorData.append("${autor.id}, ${autor.name}, ${autor.countryOfOrigin}, ${autor.dateOfBirth}, ${autor.yearOfBirth}, ${autor.mainGenre}\n")
                    }
                }
            }
            else {
                Toast.makeText(this, "Ingrese un país", Toast.LENGTH_SHORT).show()
            }
        }
        binding.BuscarPorAnyo.setOnClickListener {
            val anyo = binding.etBirthYear.text.toString().trim()
            if (anyo.isNotEmpty()) {
                val autores = database.authorsDao.getByYear(anyo)
                binding.tvAuthorData.text = ""
                autores.forEach { autor ->
                    binding.tvAuthorData.append("${autor.id}, ${autor.name}, ${autor.countryOfOrigin}, ${autor.dateOfBirth}, ${autor.yearOfBirth}, ${autor.mainGenre}\n")
                }
                if (autores.isEmpty()) {
                    binding.tvAuthorData.text = "No se encontraron autores"
                }
            }
            else {
                Toast.makeText(this, "Ingrese un año", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLol.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/S3Jon/TX2425/tree/Room-Ejemplo")
            )
            ContextCompat.startActivity(this, intent, null)
        }
    }
}