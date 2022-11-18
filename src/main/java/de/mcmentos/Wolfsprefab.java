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

    // Check if the debug mode is enabled
    final Boolean debug = getConfig().getBoolean("config.debug");
    // initialize the instance
    public static Wolfsprefab instance;
    @Override
    public void onEnable() {
        // Load Config file
        loadConfig();
        // Set the instance to this plugin
        instance = this;
        // Let the Plugin generate a Prefab folder
        PrefabFileHandler.createfolder();
        CommandRegisterer.registerCommands();
        EventRegisterer.registerEvents();
        // Set up the Cooldowns
        CooldownManager.setupCooldown();
        // If World Edit is missing on the server, give a warning in console and disable this plugin
        if(Bukkit.getPluginManager().getPlugin("WorldEdit") == null){
            Bukkit.getConsoleSender().sendMessage("§c---------------------------------");
            Bukkit.getConsoleSender().sendMessage(String.format("§d[%s]§c World Edit nicht gefunden!", getDescription().getName()));
            Bukkit.getConsoleSender().sendMessage("§c---------------------------------");
            getServer().getPluginManager().disablePlugin(this);
        }
        // When the plugin has loaded, give a console message
        Bukkit.getConsoleSender().sendMessage(String.format("§d[%s]§r Plugin geladen! Version:§a %s", getDescription().getName(), getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage("§d----------------------------------");
        // Get the number of files in the prefab folder
        Integer files = Objects.requireNonNull(PrefabFileHandler.prefab_folder.listFiles()).length;
        Bukkit.getConsoleSender().sendMessage(String.format("'%s' Prefabs geladen!", files));
        // Send a message if the debug mode is enabled or disabled
        if(debug) {
            Bukkit.getConsoleSender().sendMessage("§bDEBUG-MODE: §aaktiviert!");
        }else{
            Bukkit.getConsoleSender().sendMessage("§bDEBUG-MODE: §cdeaktiviert!");
        }
        Bukkit.getConsoleSender().sendMessage("§d----------------------------------");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(String.format("§d[%s]§r Auf Wiedersehen!", getDescription().getName()));
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    // Getter for the instance
    public static Plugin getInstance() {
        return instance;
    }

    // Getter for the debug mode
    public Boolean isDebugEnabled(){return debug;}

}
