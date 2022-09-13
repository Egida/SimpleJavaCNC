package xyz.terrific.bot.command

import xyz.terrific.bot.Bot
import xyz.terrific.bot.command.commands.ExecuteShell
import xyz.terrific.bot.command.commands.Test
import java.util.*

/**
 * @author TerrificTable55
 * @version 1.0
 */
class CommandManager {
    /**
     * @use CommandManager initialization, it adds commands to a `public commands` list
     */
    init {
        addCommand(Test())
        addCommand(ExecuteShell())
    }

    fun addCommand(command: Command) {
        commands.add(command)
    }

    /**
     * @use Handle Commands, it takes a message and a bot as arguments, it will remove the `>`'s from the command and call the `command.onCommand()` function, returns a String
     * @param message
     * @param bot
     * @return
     */
    fun handleMessage(message: String, bot: Bot?): String {
        var message = message
        message = message.replace("> ", "")
        message = message.replace(">", "")
        var found = false
        if (message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().isEmpty()) return ""
        println(message)
        val commandName = message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        for (cmd in commands) {
            if (cmd.aliases.contains(commandName) || cmd.name.equals(commandName, ignoreCase = true)) {
                cmd.onCommand(Arrays.copyOfRange(message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray(),
                    1,
                    message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size), message, bot)
                found = true
                break
            }
        }
        return if (!found) {
            "ERROR: Command not found"
        } else "Success"
    }

    companion object {
        var commands = ArrayList<Command>()
        fun getCommands(): List<Command> {
            return commands
        }
    }
}
