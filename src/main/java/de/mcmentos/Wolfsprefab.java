package de.mcmentos;

import de.mcmentos.utils.CommandRegisterer;
import de.mcmentos.utils.CooldownManager;
import de.mcmentos.utils.EventRegisterer;
import de.mcmentos.utils.PrefabFileHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Wolfsprefab extends JavaPlugin {

    final Boolean debug = getConfig().getBoolean("config.debug");
    public static Wolfsprefab instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfig();
        instance = this;
        PrefabFileHandler.createfolder();
        CommandRegisterer.registerCommands();
        EventRegisterer.registerEvents();
        CooldownManager.setupCooldown();
        if(Bukkit.getPluginManager().getPlugin("WorldEdit") == null){
            Bukkit.getConsoleSender().sendMessage("§c---------------------------------");
            Bukkit.getConsoleSender().sendMessage(String.format("§d[%s]§c World Edit nicht gefunden!", getDescription().getName()));
            Bukkit.getConsoleSender().sendMessage("§c---------------------------------");
            getServer().getPluginManager().disablePlugin(this);
        }
        // Startup finished
        Bukkit.getConsoleSender().sendMessage(String.format("§d[%s]§r Plugin geladen! Version:§a %s", getDescription().getName(), getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage("§d---------------------------------");
        Integer files = Objects.requireNonNull(PrefabFileHandler.folder.listFiles()).length;
        Bukkit.getConsoleSender().sendMessage(String.format("'%s' Prefabs geladen!", files));
        if(debug) {
            Bukkit.getConsoleSender().sendMessage("§bDEBUG-MODE: §aaktiviert!");
        }else{
            Bukkit.getConsoleSender().sendMessage("§bDEBUG-MODE: §caktiviert!");
        }
        Bukkit.getConsoleSender().sendMessage("§d---------------------------------");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(String.format("§d[%s]§r Auf Wiedersehen!", getDescription().getName()));
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public static Plugin getInstance() {
        return instance;
    }

    public Boolean isDebugEnabled(){return debug;}

}
