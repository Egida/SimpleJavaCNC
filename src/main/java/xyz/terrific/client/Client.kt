package xyz.terrific.client

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.InetAddress
import java.net.Socket
import java.util.*

/**
 * @author TerrificTable55
 * @version 1.0
 */
class Client(socket: Socket) {
    private val socket: Socket
    private val reader: BufferedReader
    private val writer: BufferedWriter

    /**
     * @use Initializes the Client, it sets hostname and socket, defines writer and reader and sends hostname to server
     * @param socket used to connect to server
     * @throws Exception
     */
    init {
        val hostname = InetAddress.getLocalHost().hostName
        this.socket = socket
        writer = BufferedWriter(OutputStreamWriter(this.socket.getOutputStream()))
        reader = BufferedReader(InputStreamReader(this.socket.getInputStream()))
        writer.write(hostname)
        writer.newLine()
        writer.flush()
        clients.add(this)
    }

    /**
     * @param message Message sent to server
     */
    fun sendMessage(message: String?) {
        try {
            writer.write(message)
            writer.newLine()
            writer.flush()
        } catch (e: Exception) {
            close(socket, reader, writer)
            print("EXCEPTION ->  sendMessage()  <-")
            e.printStackTrace()
        }
    }

    /**
     * @use Waits for a message / command to be inputed by a user and sends that message / command to the server
     */
    fun inputMessage() {
        try {
            val scanner = Scanner(System.`in`)
            while (socket.isConnected) {
                print(" > ")
                var message: String = scanner.nextLine().strip().replace("\n", "")
                if (message == "exit") {
                    close(socket, reader, writer)
                    break
                }
                message = if (message.contains("bot=")) {
                    "> $message"
                } else {
                    ">> $message"
                }
                sendMessage(message)
            }
        } catch (e: Exception) {
            close(socket, reader, writer)
            print("EXCEPTION ->  inputMessage()  <-")
            e.printStackTrace()
        }
    }

    fun removeClient() {
        clients.remove(this)
        println("Closed Connection")
    }

    /**
     * @param socket closes socket
     * @param reader closes reader
     * @param writer closes writer
     */
    fun close(socket: Socket?, reader: BufferedReader?, writer: BufferedWriter?) {
        removeClient()
        try {
            reader?.close()
            writer?.close()
            socket?.close()
        } catch (e: Exception) {
            print("EXCEPTION ->  close()  <-")
            e.printStackTrace()
        }
    }

    companion object {
        var clients = ArrayList<Client>()
    }
}
