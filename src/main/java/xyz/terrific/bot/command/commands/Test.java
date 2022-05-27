package xyz.terrific.bot.command.commands;

import xyz.terrific.bot.Bot;
import xyz.terrific.bot.command.Command;

import java.util.Arrays;

/**
 * @author TerrificTable55
 * @version 1.0
 */
public class Test extends Command {

    /**
     * @use Test Command Constructor, it defines a name / aliases and a definition, if you want you can define a syntax too
     */
    public Test() {
        super("test", "test command");
        this.setSyntax("test");
    }

    /**
     * Takes args, command and bot as arguments, sends `Test!` and the args to the server
     * @param args
     * @param command
     * @param bot
     */
    @Override
    public void onCommand(String[] args, String command, Bot bot) {

        bot.sendMessage("Test!");
        bot.sendMessage(Arrays.toString(args));

    }
}
