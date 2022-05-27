package xyz.terrific.bot.command.commands;

import xyz.terrific.bot.Bot;
import xyz.terrific.bot.command.Command;

import java.util.Arrays;

public class Test extends Command {
    public Test() {
        super("test", "test command");
        this.setSyntax("test");
    }

    @Override
    public void onCommand(String[] args, String command, Bot bot) {

        bot.sendMessage("Test!");
        bot.sendMessage(Arrays.toString(args));

    }
}
