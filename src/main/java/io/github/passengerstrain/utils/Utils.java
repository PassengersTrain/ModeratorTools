package io.github.passengerstrain.utils;

import io.github.passengerstrain.ModeratorTools;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    private FileConfiguration fileConfiguration;

    private FileConfiguration languageFileConfiguration;

    private final ModeratorTools plugin;

    public Utils(ModeratorTools plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        return this.fileConfiguration;
    }

    public FileConfiguration getLanguageConfig() {
        return this.languageFileConfiguration;
    }

    public void createConfigurationFile() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
                plugin.saveResource("config.yml", false);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            sendDebugMessage(Level.SEVERE, e.getMessage());
        }
    }

    public void createLanguageFile() {
        File languageFile = new File(plugin.getDataFolder(), "en_US.yml");
        if (!languageFile.exists()) {
            languageFile.getParentFile().mkdirs();
            plugin.saveResource("en_US.yml", false);
        }

        languageFileConfiguration = new YamlConfiguration();
        try {
            languageFileConfiguration.load(languageFile);
        } catch (IOException | InvalidConfigurationException e) {
            sendDebugMessage(Level.SEVERE, e.getMessage());
        }
    }

    public void sendDebugMessage(Level logLevel, String debugMessage) {
        boolean shouldPrintDebugMessage = fileConfiguration.getBoolean("options.should-print-debug-message-to-console");
        if (shouldPrintDebugMessage) {
            Logger pluginLogger = plugin.getLogger();
            pluginLogger.log(logLevel, debugMessage);
        }
    }
}
