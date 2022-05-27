package xyz.terrific.bot.command.commands;

import xyz.terrific.bot.Bot;
import xyz.terrific.bot.command.Command;

import java.io.IOException;


/**
 * @author TerrificTable55
 * @version 1.0
 */
public class ExecuteShell extends Command {
    public ExecuteShell() {
        super("sh", "Execute Shell Commands");
    }

    @Override
    public void onCommand(String[] args, String command, Bot bot) {

        try {
            StringBuilder cmd = new StringBuilder();
            for (String arg : args) {
                cmd.append(arg).append(" ");
            }

            Process proc = Runtime.getRuntime().exec(cmd.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
