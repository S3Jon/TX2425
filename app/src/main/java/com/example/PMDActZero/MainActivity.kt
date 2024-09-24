package com.example.PMDActZero

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.PMDActZero.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMostrar.setOnClickListener {
            val currentText = binding.textaco.text.toString()
            if (currentText == "Pulsa el botón") {
                binding.textaco.text = "Hola Mundo"
            } else if (currentText == "Hola Mundo") {
                binding.textaco.text = "Pulsa el botón"
            }
        }
    }
}
