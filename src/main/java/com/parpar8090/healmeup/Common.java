package com.parpar8090.healmeup;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Common {
    public static String errorPrefix = "&8[&cError&8] &7",
            warningPrefix = "&c[&6Warning&c] &7",
            successPrefix = "&8[&aSuccess&8] &7",
            prefix = "",
            pluginPrefix = "";

    /**
     * Will the {@link Common#tell(Player, String...)} include the {@link Common#pluginPrefix} prefix?
     */
    public static boolean tellPluginPrefixed = true;

    /**
     * @return Colored error prefix
     */
    public static String getErrorPrefix() {
        return colorize(errorPrefix);
    }

    /**
     * @return Colored warning prefix
     */
    public static String getWarningPrefix() {
        return colorize(warningPrefix);
    }

    /**
     * @return Colored success prefix
     */
    public static String getSuccessPrefix() {
        return colorize(successPrefix);
    }

    /**
     * @return Colored prefix
     */
    public static String getPrefix() {
        return colorize(prefix);
    }

    /**
     * @return Colored plugin prefix
     */
    public static String getPluginPrefix() {
        return colorize(pluginPrefix);
    }

    private static String spacedPrefix(String prefix){
        return prefix.endsWith(" ") || prefix.length()==0 ? prefix : prefix+" ";
    }

    private static String pluginPrefixed(String tellMessage){
        return tellPluginPrefixed ? spacedPrefix(pluginPrefix) + tellMessage : tellMessage;
    }

    public static void log(List<String> messages){
        for(String message : messages){
            log(message);
        }
    }

    public static void log(String message){
        Bukkit.getLogger().info(colorize((spacedPrefix(getPluginPrefix()) +message)));
    }

    public static void logInfo(String message){
        log(spacedPrefix(getPrefix()) + message);
    }

    public static void logError(String message){
        Bukkit.getLogger().severe(colorize(spacedPrefix(getErrorPrefix()) + message));
    }

    public static void logWarning(String message){
        Bukkit.getLogger().warning(colorize(spacedPrefix(getWarningPrefix()) + message));
    }

    public static void logSuccess(String message){
        log(spacedPrefix(getSuccessPrefix()) + message);
    }

    /**
     * Will not include {@link Common#pluginPrefix}
     */
    public static void tellNoPrefix(CommandSender receiver, List<String> messages){
        for(String message : messages) {
            receiver.sendMessage(colorize(message));
        }
    }

    public static void tellNoPrefix(CommandSender receiver, String message){
        receiver.sendMessage(colorize(message));
    }

    public static void tell(CommandSender receiver, List<String> messages){
        for(String message : messages) {
            tellNoPrefix(receiver, spacedPrefix(getPrefix()) + message);
        }
    }

    public static void tellError(CommandSender receiver, String message){
        tellNoPrefix(receiver, spacedPrefix(getErrorPrefix()) + message);
    }

    public static void tellWarning(CommandSender receiver, String message){
        tellNoPrefix(receiver, spacedPrefix(getWarningPrefix()) + message);
    }

    public static void tellSuccess(CommandSender receiver, String message){
        tellNoPrefix(receiver, spacedPrefix(getSuccessPrefix()) + message);
    }

    public static String colorize(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
