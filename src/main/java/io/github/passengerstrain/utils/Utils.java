package io.github.passengerstrain.utils;

import io.github.passengerstrain.ModeratorTools;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Utils {

    private File file;

    private FileConfiguration fileConfiguration;

    private File languageFile;

    private FileConfiguration languageFileConfiguration;

    private ModeratorTools plugin;

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
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void createLanguageFile() {
        languageFile = new File(plugin.getDataFolder(), "en_US.yml");
        if (!languageFile.exists()) {
            languageFile.getParentFile().mkdirs();
            plugin.saveResource("en_US.yml", false);
        }

        languageFileConfiguration = new YamlConfiguration();
        try {
            languageFileConfiguration.load(languageFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfigFile() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveLanguageFile() {
        try {
            languageFileConfiguration.save(languageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendDebugMessage(Level logLevel, String debugMessage) {
        boolean shouldPrintDebugMessage = fileConfiguration.getBoolean("options.consoleShouldPrintDebugMessage");
        if(shouldPrintDebugMessage == true) {
            System.out.println(logLevel + " " + debugMessage);
        }
    }

}
