package de.mcmentos.utils;

import de.mcmentos.Wolfsprefab;
import de.mcmentos.events.InteractEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventRegisterer {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static String Prefix = Plugin.getName();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();

    public static void registerEvents() {
        if(debug){
            Bukkit.getConsoleSender().sendMessage(String.format("[%s] Registering Events...", Prefix));
        }
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InteractEvents(), Plugin);
    }
}
