package com.example.psp_cronometro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.psp_cronometro.ui.theme.PSPCronometroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PSPCronometroTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TimerApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TimerApp(modifier: Modifier = Modifier) {
    var decimas by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatTime(decimas), style = MaterialTheme.typography.headlineLarge)

        Button(onClick = {
            if (!isRunning) {
                isRunning = true
                Thread {
                    while (isRunning) {
                        Thread.sleep(100)
                        decimas += 1
                    }
                }.start()
            }
        }) {
            Text("Start")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = {
            isRunning = false
        }) {
            Text("Pause")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = {
            isRunning = false
            decimas = 0
        }) {
            Text("Restart")
        }
    }
}

/* Nota: Guardo las décimas de segundo para que
         al pausar el timer si han pasado 0.9 segundos
         se mantenga, para no poder spammear y que así siempre
         tener el mismo tiempo a pesar de que ha pasado
*/
fun formatTime(decimas: Int): String {
    val totalSegundos = decimas / 10
    val horas = totalSegundos / 3600
    val minutos = (totalSegundos % 3600) / 60
    val segundos = totalSegundos % 60
    return String.format("%02d:%02d:%02d", horas, minutos, segundos)
}

@Preview(showBackground = true)
@Composable
fun TimerAppPreview() {
    PSPCronometroTheme {
        TimerApp()
    }
}
