package com.parpar8090.healmeup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HealMeUpCommand extends Command {
    public static final String PERMISSION = "healmeup.command";

    protected HealMeUpCommand() {
        super("healmeup", "Settings for HealMeUp", "/healmeup <reload|set>", List.of("hmu"));
        setPermission(PERMISSION);
        setPermissionMessage("");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!sender.hasPermission(PERMISSION)) return true;
        //todo
        if(args.length == 0){
            tellMissingError(sender, label);
            return true;
        }
        if (args[0].equalsIgnoreCase("help")){
            sendHelpMessage(sender, label);
            return true;
        }

        else if(args[0].equalsIgnoreCase("reload")){
            HealMeUp.getInstance().getSettings().loadSettings();
            Common.tellSuccess(sender, "Reloaded HealMeUp settings!");
        }

        else if(args[0].equalsIgnoreCase("set")){
            if(args.length != 3){
                tellMissingError(sender, label);
                return true;
            }
            try {
                switch (args[1]) {
                    case "heal-rate": {
                        HealMeUp.getInstance().getSettings().setAndSaveSetting("heal-rate", Integer.parseInt(args[2]));
                        Common.tellSuccess(sender, "Set and saved healing-rate to "+args[2]);
                        break;
                    }
                    case "combat-timeout":{
                        HealMeUp.getInstance().getSettings().setAndSaveSetting("combat-timeout", Integer.parseInt(args[2]));
                        Common.tellSuccess(sender, "Set and saved combat-timeout to "+args[2]);
                    break;}
                    case "combat-pvp-only": {
                        HealMeUp.getInstance().getSettings().setAndSaveSetting("combat-pvp-only", parseBoolean(args[2]));
                        Common.tellSuccess(sender, "Set and saved combat-pvp-only to "+args[2]);
                        break;
                    }
                    case "replace-vanilla-healing": {
                        HealMeUp.getInstance().getSettings().setAndSaveSetting("replace-vanilla-healing", parseBoolean(args[2]));
                        Common.tellSuccess(sender, "Set and saved replace-vailla-healing to "+args[2]);
                        break;
                    }
                    case "stop-healing-once-full": {
                        HealMeUp.getInstance().getSettings().setAndSaveSetting("stop-healing-once-full", parseBoolean(args[2]));
                        Common.tellSuccess(sender, "Set and saved stop-healing-once-full to "+args[2]);
                        break;
                    }
                    default: {
                        tellMissingError(sender, label);
                        break;
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
                Common.tellError(sender, "You have specified an invalid integer number! Make sure it's above 0");
            }
        }

        else {
            tellMissingError(sender, label);
            return true;
        }

        return true;
    }

    private Boolean parseBoolean(String text) throws Exception {
        if(text.equalsIgnoreCase("true")) return true;
        if(text.equalsIgnoreCase("false")) return false;
        throw new Exception("Invalid boolean value!");
    }

    private void sendHelpMessage(CommandSender sender, String label){
        List<String> lines = new ArrayList<>();
        lines.add("&aSub-commands:");
        lines.add("&7 /"+label+" &areload&7 - reloads settings file");
        lines.add("&7 /"+label+" &aset&7 <heal-rate|combat-timeout|combat-pvp-only|replace-vailla-healing|stop-healing-once-full> - modifies plugin settings");
        Common.tell(sender, lines);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(!isPlayer(sender) || !sender.hasPermission(PERMISSION)) {
            return new ArrayList<>();
        }

        if(args.length == 1){
            return List.of("set", "reload");
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("set")){
            return List.of("heal-rate", "combat-timeout", "combat-pvp-only", "replace-vailla-healing", "stop-healing-once-full");
        }
        return new ArrayList<>();
    }

    public void tellMissingError(CommandSender sender, String label){
        Common.tellError(sender, "No command arguments specified! Please run &n/"+label+" help&7 for help!");
    }

    public boolean isPlayer(CommandSender sender){
        return sender instanceof Player;
    }
}
