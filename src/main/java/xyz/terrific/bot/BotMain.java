package xyz.terrific.bot;

import xyz.terrific.bot.command.CommandManager;

public class BotMain {

    public static CommandManager commandManager;

    public static void main(String[] args) throws Exception {

        commandManager = new CommandManager();

        Bot.init();

    }

}
