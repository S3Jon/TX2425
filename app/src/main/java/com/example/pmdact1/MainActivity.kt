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
        var text by remember {mutableStateOf("Introduce un número primo")}
        var btText by remember {mutableStateOf("Comprobar")}
        var textField by remember { mutableStateOf("")}
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
            Arrangement.spacedBy(20.dp)) {
            Text(text)

            TextField(
                value = textField,
                onValueChange = { textField = it },
                label = {Text("Tu número")},
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { //Quedaría más limpio en función, pero pereza
                if (!textField.isEmpty() && btText != "Reiniciar") {
                    var nem = textField.toIntOrNull()
                    btText = "Reiniciar"
                    if (nem == null || nem < 1) {
                        text = "No es un número primo"
                        return@Button
                    }
                    for (i in 2..nem / 2) {
                        if (nem % i == 0) {
                            text = "No es un número primo"
                            return@Button
                        }
                    }
                    text = "Es número primo"
                }
                else {
                    text = "Introduce un numero primo"
                    textField = ""
                    btText = "Comprobar"
                }
            }) {
                Text(btText)
            }
        }

    }
}