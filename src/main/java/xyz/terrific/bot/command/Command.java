package xyz.terrific.bot.command;

import xyz.terrific.bot.Bot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author TerrificTable55
 * @version 1.0
 */
public abstract class Command {

    public String                   name;
    public String                   description;
    public String                   syntax;
    public List<String>             aliases;


    /**
     * @use One of the three ways to define a new Command, takes a new name and description
     * @param name
     * @param description
     */
    public Command(String name, String description) {
        super();
        this.name = name;
        this.description = description;
        this.syntax = "";
        this.aliases = Collections.singletonList(name);
    }

    /**
     * @use Second way to define a new Command, takes a new name, description and aliases
     * @param name
     * @param description
     * @param aliases
     */
    public Command(String name, String description, String[] aliases) {
        super();
        this.name = name;
        this.description = description;
        this.syntax = "";
        this.aliases = Arrays.asList(aliases);
    }

    /**
     * @use Third and last way to define a new Command, takes a new name, description, syntax and aliases
     * @param name
     * @param description
     * @param syntax
     * @param aliases
     */
    public Command(String name, String description, String syntax, String[] aliases) {
        super();
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }


    /**
     * @use the `onCommand` function, its called when a command is sent to the bot, takes args, command and bot as arguments
     * @param args
     * @param command
     * @param bot
     */
    public abstract void onCommand(String[] args, String command, Bot bot);
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSyntax() {
        return syntax;
    }
    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }
    public List<String> getAliases() {
        return aliases;
    }
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
