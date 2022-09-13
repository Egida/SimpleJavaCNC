package xyz.terrific.bot

import xyz.terrific.bot.command.CommandManager

/**
 * @author TerrificTable55
 * @version 1.0
 */
object BotMain {
    var commandManager: CommandManager? = null

    /**
     * @use Start Bot
     * @param args
     * @throws Exception
     */
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        commandManager = CommandManager()
        Bot.init()
    }
}
