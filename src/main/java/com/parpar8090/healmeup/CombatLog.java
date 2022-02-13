package com.parpar8090.healmeup;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CombatLog {
    public static final HashMap<UUID, CombatLog> combatLogs = new HashMap<>();

    public int taskId = -1;
    public int timeLeft = 0;

    private final UUID uuid;

    public CombatLog(UUID uuid){
        this.uuid = uuid;

        combatLogs.put(uuid, this);
    }

    public static CombatLog getCombatLog(UUID playerUUID){
        if(combatLogs.get(playerUUID) == null){
            new CombatLog(playerUUID);
        }
        return combatLogs.get(playerUUID);
    }

    public void startTimeout(){
        if(taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
        timeLeft = Settings.COMBAT_TIMEOUT;
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(HealMeUp.getInstance(), () -> {
            if(Bukkit.getPlayer(uuid) == null) timeLeft = Settings.COMBAT_TIMEOUT;

            if(timeLeft > 0) {
                timeLeft--;
            } else {
                timeLeft = 0;
                startHealing();
            }
        },0, 20);
    }

    public void startHealing(){
        if(taskId != -1) cancelTask();
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(HealMeUp.getInstance(), () -> {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) return; //skips if player is offline

            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            if(player.getHealth() < maxHealth) {
                player.setHealth(Math.min(player.getHealth() + Settings.HEAL_RATE, maxHealth));
            } else {
                if(Settings.STOP_HEALING_ONCE_FULL) {
                    cancelTask();
                }
            }
        },0, 20);
    }

    public void cancelTask(){
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
