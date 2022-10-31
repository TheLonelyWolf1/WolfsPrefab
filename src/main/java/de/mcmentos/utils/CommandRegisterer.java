package de.mcmentos.utils;

import de.mcmentos.Wolfsprefab;
import de.mcmentos.commands.ClassicHouseCommand;
import de.mcmentos.commands.DirtHouseCommand;
import de.mcmentos.commands.HelpCommand;
import org.bukkit.Bukkit;

import java.util.Objects;

public class CommandRegisterer {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static String Prefix = Plugin.getName();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();

    public static void registerCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("wphelp")).setExecutor(new HelpCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("wpdirthouse")).setExecutor(new DirtHouseCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("wpclassichouse")).setExecutor(new ClassicHouseCommand());

    }
}
