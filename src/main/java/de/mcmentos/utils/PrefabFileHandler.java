package de.mcmentos.utils;

import de.mcmentos.Wolfsprefab;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class PrefabFileHandler {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();
    public static File folder = new File(Plugin.getDataFolder(), "prefabs" + File.separator);
    static File df = Plugin.getDataFolder();

    public static void createfolder() {
        if(!folder.exists()){
            if(debug){
                Bukkit.getConsoleSender().sendMessage("§d[%s]§r Erstelle Ordner für Prefabs.");
            }
            folder.mkdir();
        }
    }

}
