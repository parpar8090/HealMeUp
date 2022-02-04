package com.parpar8090.healmeup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onHeal(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player){
            if(Settings.REPLACE_VANILLA_HEALING && e.getRegainReason() != EntityRegainHealthEvent.RegainReason.CUSTOM)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(Settings.COMBAT_PVP_ONLY) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                CombatLog.getCombatLog(e.getEntity().getUniqueId()).startTimeout();
                CombatLog.getCombatLog(e.getDamager().getUniqueId()).startTimeout();
            }
        } else {
            if(e.getDamager() instanceof Player) {
                CombatLog.getCombatLog(e.getDamager().getUniqueId()).startTimeout();
            }

            if(e.getEntity() instanceof Player){
                CombatLog.getCombatLog(e.getEntity().getUniqueId()).startTimeout();
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(CombatLog.getCombatLog(e.getEntity().getUniqueId()).taskId != -1)
            Bukkit.getScheduler().cancelTask(CombatLog.getCombatLog(e.getEntity().getUniqueId()).taskId);
    }
}
