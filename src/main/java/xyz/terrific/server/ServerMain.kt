package xyz.terrific.server

import xyz.terrific.server.bot.cnc.BotServer
import xyz.terrific.server.client.cnc.ClientServer
import java.io.IOException

/**
 * @author TerrificTable55
 * @version 1.0
 */
object ServerMain {
    /**
     * @use  Creates a new BotServer and a new ClientServer
     * @param args
     */
    @JvmStatic
    fun main(args: Array<String>) {
        Thread {
            try {
                BotServer.init()
            } catch (e: IOException) {
                println("[ ERROR | Server | Bot ]  " + e.message)
            }
        }.start()
        Thread {
            try {
                ClientServer.init()
            } catch (e: IOException) {
                println("[ ERROR | Server | Client ]  " + e.message)
            }
        }.start()
    }
}
