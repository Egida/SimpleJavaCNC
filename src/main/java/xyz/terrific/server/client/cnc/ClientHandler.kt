package xyz.terrific.server.client.cnc

import xyz.terrific.server.bot.cnc.BotHandler
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

/**
 * @author TerrificTable55
 * @version 1.0
 */
class ClientHandler(socket: Socket?) : Runnable {
    private var name: String? = null
    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    var writer: BufferedWriter? = null

    /**
     * @use ClientHandler initializer, defines writer and reader
     * @param socket sets private socket variable
     */
    init {
        try {
            this.socket = socket
            writer = BufferedWriter(OutputStreamWriter(this.socket!!.getOutputStream()))
            reader = BufferedReader(InputStreamReader(this.socket!!.getInputStream()))
            name = reader!!.readLine()
            handlers.add(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * @use Implements `run` function from Runnable interface
     */
    override fun run() {
        while (socket!!.isConnected) {
            try {
                val message = reader!!.readLine()
                System.out.printf(" [ CLIENT | %s ]  %s\n", name, message)
                if (message.startsWith("> ")) {
                    sendAll(message)
                } else if (message.startsWith(">> ")) {
                    send(message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1], message)
                }
            } catch (e: Exception) {
                close(socket, reader, writer)
                e.printStackTrace()
                return
            }
        }
    }

    /**
     * @param command this command is sent to all clients / bots
     */
    fun sendAll(command: String?) {
        for (handler in BotHandler.handlers) {
            try {
                if (handler.name != name) {
                    handler.writer!!.write(command)
                    handler.writer!!.newLine()
                    handler.writer!!.flush()
                }
                break
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * @use removes handler from `handlers` list
     */
    fun removeHandler() {
        handlers.remove(this)
        System.out.printf(" [ CLIENT | %s ]  Closed Connection\n", name)
    }

    /**
     * @param socket closes socket
     * @param reader closes reader
     * @param writer closes writer
     */
    fun close(socket: Socket?, reader: BufferedReader?, writer: BufferedWriter?) {
        removeHandler()
        try {
            reader?.close()
            writer?.close()
            socket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        var handlers = ArrayList<ClientHandler>()

        /**
         * @use Sends a message to the clients
         * @param botName sends message to this bot / client
         * @param command sends this command
         */
        fun send(botName: String, command: String?) {
            for (handler in BotHandler.handlers) {
                try {
                    if (handler.name != botName) {
                        handler.writer!!.write(command)
                        handler.writer!!.newLine()
                        handler.writer!!.flush()
                    }
                    break
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
