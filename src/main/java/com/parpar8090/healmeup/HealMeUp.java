package com.parpar8090.healmeup;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class HealMeUp extends JavaPlugin {

    /*  HealMeUp
    Heal player when out of fight for x seconds (by default 10).
    Heals at a rate of x hp per second (2 by default). This replaces the default healing mechanic.
    */

    @Getter
    private static HealMeUp instance;

    @Getter
    private Settings settings;

    public HealMeUp(){
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Common.pluginPrefix = "[HealMeUp]";

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        HealMeUpCommand command = new HealMeUpCommand();

        try {
            final Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            commandMap.setAccessible(true);
            CommandMap map = (CommandMap) commandMap.get(Bukkit.getServer());

            map.register("healmeup", command);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // creates/loads config
        settings = new Settings();

        Common.logSuccess("Enabled HealMeUp plugin!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getScheduler().cancelTasks(this);

        Common.log("Disabled HealMeUp plugin!");
    }


}
