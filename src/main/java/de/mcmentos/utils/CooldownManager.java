package de.mcmentos.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    public static void setupCooldown(){
        cooldownPrefab = new HashMap<>();
    }

    public static HashMap<UUID, Double> cooldownPrefab;

    public static void setcooldownPrefab(Player player, int seconds){
        double delay = System.currentTimeMillis() + (seconds* 1000L);
        cooldownPrefab.put(player.getUniqueId(), delay);
    }
    //getCooldown
    public static int getcooldownPrefab(Player player){
        return Math.toIntExact(Math.round((cooldownPrefab.get(player.getUniqueId()) - System.currentTimeMillis())/1000));
    }
    //checkCooldown
    public static boolean checkcooldownPrefab(Player player){
        return !cooldownPrefab.containsKey(player.getUniqueId()) || cooldownPrefab.get(player.getUniqueId()) <= System.currentTimeMillis();
    }
}
