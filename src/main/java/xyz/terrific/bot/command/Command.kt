package xyz.terrific.bot.command

import xyz.terrific.bot.Bot
import java.util.*

/**
 * @author TerrificTable55
 * @version 1.0
 */
abstract class Command {
    var name: String
    var description: String
    var syntax: String
    var aliases: List<String>

    /**
     * @use One of the three ways to define a new Command, takes a new name and description
     * @param name
     * @param description
     */
    constructor(name: String, description: String) : super() {
        this.name = name
        this.description = description
        syntax = ""
        aliases = listOf(name)
    }

    /**
     * @use Second way to define a new Command, takes a new name, description and aliases
     * @param name
     * @param description
     * @param aliases
     */
    constructor(name: String, description: String, aliases: Array<String?>) : super() {
        this.name = name
        this.description = description
        syntax = ""
        this.aliases = Arrays.asList(*aliases) as List<String>
    }

    /**
     * @use Third and last way to define a new Command, takes a new name, description, syntax and aliases
     * @param name
     * @param description
     * @param syntax
     * @param aliases
     */
    constructor(name: String, description: String, syntax: String, aliases: Array<String?>) : super() {
        this.name = name
        this.description = description
        this.syntax = syntax
        this.aliases = Arrays.asList(*aliases) as List<String>
    }

    /**
     * @use the `onCommand` function, its called when a command is sent to the bot, takes args, command and bot as arguments
     * @param args
     * @param command
     * @param bot
     */
    abstract fun onCommand(args: Array<String?>?, command: String?, bot: Bot?)
}
