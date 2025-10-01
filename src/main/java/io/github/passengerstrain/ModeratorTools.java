package io.github.passengerstrain;

import io.github.passengerstrain.commands.CommandManager;
import io.github.passengerstrain.listeners.PlayerListener;
import io.github.passengerstrain.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ModeratorTools extends JavaPlugin {
    private Utils utils;

    @Override
    public void onEnable() {
        this.utils = new Utils(this);

        utils.createConfigurationFile();
        utils.sendDebugMessage(Level.INFO, "Successfully loaded plugin configuration file.");

        utils.createLanguageFile();
        utils.sendDebugMessage(Level.INFO, "Successfully loaded plugin language configuration file.");

        registerPluginEvents();
        utils.sendDebugMessage(Level.INFO, "Successfully registered plugin events.");

        registerPluginCommands();
        utils.sendDebugMessage(Level.INFO, "Successfully registered plugin commands.");

        utils.sendDebugMessage(Level.INFO, "Successfully enabled moderator-tools.");
    }

    @Override
    public void onDisable() {
        utils.saveConfigFile();
        utils.sendDebugMessage(Level.INFO, "Successfully saved plugin configuration file.");

        utils.saveLanguageFile();
        utils.sendDebugMessage(Level.INFO, "Successfully saved plugin language configuration file.");

        utils.sendDebugMessage(Level.INFO, "Successfully disabled moderator-tools.");
    }

    private void registerPluginCommands() {
        getCommand("moderatortools").setExecutor(new CommandManager());
    }

    private void registerPluginEvents() {
        getServer().getPluginManager().registerEvents(new PlayerListener(utils), this);
    }
}
