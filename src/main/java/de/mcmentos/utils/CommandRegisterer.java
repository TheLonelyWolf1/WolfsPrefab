package de.mcmentos.utils;

import de.mcmentos.Wolfsprefab;
import de.mcmentos.commands.*;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandRegisterer {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static String Prefix = Plugin.getName();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();

    static List< String > subcommands = new ArrayList<>();

    public static void registerCommands() {
        if(debug){
            Bukkit.getConsoleSender().sendMessage(String.format("[%s] Registering Commands...", Prefix));
        }
        Objects.requireNonNull(Bukkit.getPluginCommand("wp")).setExecutor(new WolfsPrefabCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("wp")).setTabCompleter(new WolfsPrefabCommand());
    }

    public static List< String> Subcommands(){
        subcommands.clear();
        subcommands.add("help");
        subcommands.add("give");
        subcommands.add("info");
        return subcommands;
    }
}
