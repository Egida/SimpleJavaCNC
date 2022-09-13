package xyz.terrific.server.client.cnc

import java.io.IOException
import java.net.ServerSocket

/**
 * @author TerrificTable55
 * @version 1.0
 */
class ClientServer
    /**
     * @param socket sets private socket variable
     */
    (private val socket: ServerSocket?) {

    /**
     * @use start function
     */
    fun start() {
        try {
            while (!socket!!.isClosed) {
                val conn = socket.accept()
                System.out.printf(" [ CLIENT | %s ] Connected\n", conn.remoteSocketAddress.toString())
                val handler = ClientHandler(conn)
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
        @Throws(IOException::class)
        fun init() {
            val cSocket = ServerSocket(1773)
            val server = ClientServer(cSocket)
            server.start()
        }
    }
}
