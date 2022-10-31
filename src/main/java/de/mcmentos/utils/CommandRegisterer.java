package de.mcmentos.utils;

import de.mcmentos.Wolfsprefab;

public class CommandRegisterer {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static String Prefix = Plugin.getName();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();

    public static void registerCommands() {

    }
}
