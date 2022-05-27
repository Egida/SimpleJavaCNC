package xyz.terrific.bot;

import xyz.terrific.bot.command.CommandManager;


/**
 * @author TerrificTable55
 * @version 1.0
 */
public class BotMain {

    public static CommandManager commandManager;

    /**
     * @use Start Bot
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        commandManager = new CommandManager();

        Bot.init();

    }

}
