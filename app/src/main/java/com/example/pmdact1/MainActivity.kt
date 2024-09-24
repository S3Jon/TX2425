package com.example.pmdact1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pmdact1.ui.theme.PMDAct1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Act1()
        }
    }
}

fun a() {
    print("a")
}

@Preview(showBackground = true)
@Composable
fun Act1() {
    PMDAct1Theme {
        val context = LocalContext.current
        val resources = context.resources
        var text by remember { mutableStateOf(resources.getString(R.string.introduce_numero_primo)) }
        var btText by remember { mutableStateOf(resources.getString(R.string.comprobar)) }
        var textField by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text)

            TextField(
                value = textField,
                onValueChange = { textField = it },
                label = { Text(resources.getString(R.string.tu_numero)) },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                if (btText != resources.getString(R.string.reiniciar)) {
                    val nem = textField.toIntOrNull()
                    btText = resources.getString(R.string.reiniciar)
                    if (nem == null || nem < 1) {
                        text = resources.getString(R.string.no_es_numero_primo)
                        return@Button
                    }
                    for (i in 2..nem / 2) {
                        if (nem % i == 0) {
                            text = resources.getString(R.string.no_es_numero_primo)
                            return@Button
                        }
                    }
                    text = resources.getString(R.string.es_numero_primo)
                } else {
                    text = resources.getString(R.string.introduce_numero_primo)
                    textField = ""
                    btText = resources.getString(R.string.comprobar)
                }
            }) {
                Text(btText)
            }
        }
    }
}