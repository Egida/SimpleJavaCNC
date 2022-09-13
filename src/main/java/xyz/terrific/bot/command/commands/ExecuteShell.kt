package xyz.terrific.bot.command.commands

import xyz.terrific.bot.Bot
import xyz.terrific.bot.command.Command
import java.io.IOException

/**
 * @author TerrificTable55
 * @version 1.0
 */
class ExecuteShell : Command("sh", "Execute Shell Commands") {
    override fun onCommand(args: Array<String?>?, command: String?, bot: Bot?) {
        try {
            val cmd = StringBuilder()
            for (arg in args!!) {
                cmd.append(arg).append(" ")
            }
            val proc = Runtime.getRuntime().exec(cmd.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
