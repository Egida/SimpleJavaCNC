package xyz.terrific.server.bot.cnc

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

/**
 * @author TerrificTable55
 * @version 1.0
 */
class BotHandler(socket: Socket?) : Runnable {
    var name: String? = null
    private var socket: Socket? = null
    var reader: BufferedReader? = null
    var writer: BufferedWriter? = null

    /**
     * @use Initialize the BotHandler, defines writer and reader, sets socket
     * @param socket used to connect to server
     */
    init {
        try {
            this.socket = socket
            writer = BufferedWriter(OutputStreamWriter(this.socket!!.getOutputStream()))
            reader = BufferedReader(InputStreamReader(this.socket!!.getInputStream()))
            name = reader!!.readLine()
            handlers.add(this)
            sendAll(String.format(" [ BOT | %s ]  Connected", name))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * @use `run` function from Runnable interface
     */
    override fun run() {
        var message: String?
        while (socket!!.isConnected) {
            try {
                message = reader!!.readLine()
                System.out.printf(" [ BOT | %s ]  %s\n", name, message)
                sendAll(String.format(" [ BOT | %s ]  %s\n", name, message))
            } catch (e: Exception) {
                close(socket, reader, writer)
                e.printStackTrace()
                return
            }
        }
    }

    /**
     * @param message the command / message that will be sent to all bots
     */
    fun sendAll(message: String?) {
        for (handler in handlers) {
            try {
                if (handler.name != name) {
                    handler.writer!!.write(message)
                    handler.writer!!.newLine()
                    handler.writer!!.flush()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                close(socket, reader, writer)
            }
        }
    }

    /**
     * @use Removes the Handler from the handlers list
     */
    fun removeHandler() {
        handlers.remove(this)
        System.out.printf(" [ BOT | %s ]  Closed Connection\n", name)
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
        var handlers = ArrayList<BotHandler>()

        /**
         * @param botName send command to bot with this name
         * @param command the command that will be sent to the bot
         */
        fun send(botName: String, command: String?) {
            for (handler in handlers) {
                try {
                    if (handler.name != botName) {
                        handler.writer!!.write(command)
                        handler.writer!!.newLine()
                        handler.writer!!.flush()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
