package io.github.passengerstrain.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void executeCommand(Player player, String args[]);

}
