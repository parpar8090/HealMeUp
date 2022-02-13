package com.parpar8090.healmeup;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Settings {

    @Getter
    private File configFile;
    @Getter
    private FileConfiguration config;

    public static int HEAL_RATE;
    public static int COMBAT_TIMEOUT;
    public static boolean COMBAT_PVP_ONLY;
    public static boolean REPLACE_VANILLA_HEALING;
    public static boolean STOP_HEALING_ONCE_FULL;

    public Settings(){
        createOrLoadFile();
        loadSettings();
    }

    public void loadSettings() {
        HEAL_RATE = config.getInt("heal-rate", 2);
        COMBAT_TIMEOUT = config.getInt("combat-timeout", 10);
        COMBAT_PVP_ONLY = config.getBoolean("combat-pvp-only", false);
        REPLACE_VANILLA_HEALING = config.getBoolean("replace-vanilla-healing", true);
        STOP_HEALING_ONCE_FULL = config.getBoolean("stop-healing-once-full", true);
    }

    public void saveSettings(){
        config.set("heal-rate", HEAL_RATE);
        config.set("combat-timeout", COMBAT_TIMEOUT);
        config.set("combat-pvp-only", COMBAT_PVP_ONLY);
        config.set("replace-vanilla-healing", REPLACE_VANILLA_HEALING);
        config.set("stop-healing-when-full", STOP_HEALING_ONCE_FULL);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAndSaveSetting(String path, Object value){
        config.set(path, value);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createOrLoadFile(){
        configFile = new File(HealMeUp.getInstance().getDataFolder(), "settings.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            HealMeUp.getInstance().saveResource("settings.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
