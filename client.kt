package com.example.psp_cliente

import java.net.Socket
import java.net.ConnectException
import java.net.SocketException

fun main(args: Array<String>) {
    val address = "localhost"
    val port = 12345
    val client = Client(address, port)
    client.run()
}

class Client(private val address: String, private val port: Int) {
    fun run() {
        try {
            val socket = Socket(address, port)
            val input = socket.getInputStream().bufferedReader()
            val output = socket.getOutputStream().bufferedWriter()

            while (true) {
                val message = readLine()
                if (message == null || message == "exit") {
                    break
                }

                output.write("$message\n")
                output.flush()

                val response = input.readLine() ?: break
                if (response == "exit") {
                    break
                }
                println("Server: $response")
            }

            socket.close()
        } catch (e: ConnectException) {
            println("No se pudo conectar al servidor. Cerrando la aplicación.")
        } catch (e: SocketException) {
            println("Se perdió la conexión con el servidor. Cerrando la aplicación.")
        } catch (e: Exception) {
            println("Ocurrió un error inesperado: ${e.message}. Cerrando la aplicación.")
        }
    }
}
