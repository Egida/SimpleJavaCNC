package xyz.terrific.client

import java.net.Socket

/**
 * @author TerrificTable55
 * @version 1.0
 */
object ClientMain {
    /**
     * @use Run the Client
     * @throws RuntimeException
     * @param args
     */
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val socket = Socket("127.0.0.1", 1773)
            val client = Client(socket)
            client.inputMessage()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
