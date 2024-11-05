package com.example.psp_servidor

import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val server = ServerSocket(12345)
    println("Servidor iniciado en el puerto 12345")
    var clientNumber = 1
    val currentUsers = AtomicInteger(0) // AtomicInteger para manejo seguro del conteo de usuarios

    while (true) {
        val client = server.accept()
        if (currentUsers.get() < 5) {
            println("Cliente $clientNumber conectado (${client.inetAddress.hostAddress})")
            currentUsers.incrementAndGet()
            println("Usuarios conectados: ${currentUsers.get()}")

            thread { ClientHandler(client, clientNumber++, currentUsers).run() }
        } else {
            println("Límite de usuarios simultáneos alcanzado")
            val writer = client.getOutputStream().bufferedWriter()
            writer.write("exit")
            writer.flush()
            client.close()
        }
    }
}

class ClientHandler(private val client: Socket, private val clientNumber: Int, private val currentUsers: AtomicInteger) : Runnable {
    override fun run() {
        try {
            val input = client.getInputStream().bufferedReader()
            val output = client.getOutputStream().bufferedWriter()

            while (true) {
                val message = input.readLine() ?: break
                if (message == "exit") break

                println("Cliente $clientNumber dice: $message")
                output.write("Servidor confirma recepción de mensaje ($message)\n")
                output.flush()
            }
        } catch (e: SocketException) {
            println("Cliente $clientNumber se ha desconectado abruptamente: ${e.message}")
        } catch (e: Exception) {
            println("Error inesperado con el cliente $clientNumber: ${e.message}")
        } finally {
            // Asegurarse de cerrar el socket y reducir el contador de usuarios conectados
            currentUsers.decrementAndGet()
            println("Cliente $clientNumber desconectado, usuarios conectados: ${currentUsers.get()}")
            try {
                client.close()
            } catch (e: Exception) {
                println("Error cerrando el socket del cliente $clientNumber: ${e.message}")
            }
        }
    }
}

