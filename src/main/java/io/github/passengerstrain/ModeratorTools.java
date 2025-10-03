package io.github.passengerstrain;

import io.github.passengerstrain.commands.CommandManager;
import io.github.passengerstrain.listeners.PlayerListener;
import io.github.passengerstrain.utils.Config;
import io.github.passengerstrain.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ModeratorTools extends JavaPlugin {
    private Utils utils;
    private Config config;

    @Override
    public void onEnable() {
        this.utils = new Utils(this);
        this.config = new Config(utils);

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
        utils.sendDebugMessage(Level.INFO, "Successfully disabled moderator-tools.");
    }

    private void registerPluginCommands() {
        getCommand("moderatortools").setExecutor(new CommandManager(config));
    }

    private void registerPluginEvents() {
        getServer().getPluginManager().registerEvents(new PlayerListener(config), this);
    }
}
