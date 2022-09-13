package xyz.terrific.bot.command.commands

import xyz.terrific.bot.Bot
import xyz.terrific.bot.command.Command
import java.util.*

/**
 * @author TerrificTable55
 * @version 1.0
 */
class Test : Command("test", "test command") {
    /**
     * @use Test Command Constructor, it defines a name / aliases and a definition, if you want you can define a syntax too
     */
    init {
        syntax = "test"
    }

    /**
     * Takes args, command and bot as arguments, sends `Test!` and the args to the server
     * @param args
     * @param command
     * @param bot
     */
    override fun onCommand(args: Array<String?>?, command: String?, bot: Bot?) {
        bot!!.sendMessage("Test!")
        bot.sendMessage(Arrays.toString(args))
    }
}
