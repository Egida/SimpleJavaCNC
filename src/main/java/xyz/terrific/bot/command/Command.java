package xyz.terrific.bot.command;

import xyz.terrific.bot.Bot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Command {

    public String                   name;
    public String                   description;
    public String                   syntax;
    public List<String>             aliases;



    public Command(String name, String description) {
        super();
        this.name = name;
        this.description = description;
        this.syntax = "";
        this.aliases = Collections.singletonList(name);
    }

    public Command(String name, String description, String[] aliases) {
        super();
        this.name = name;
        this.description = description;
        this.syntax = "";
        this.aliases = Arrays.asList(aliases);
    }

    public Command(String name, String description, String syntax, String[] aliases) {
        super();
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }



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
