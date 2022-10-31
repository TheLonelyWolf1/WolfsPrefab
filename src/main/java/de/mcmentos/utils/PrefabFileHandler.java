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
    static String Prefix = Plugin.getName();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();
    static File prefab_yaml;
    public static ConfigurationSection config;
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

    public static ConfigurationSection loadPrefabYaml(String name) {
        prefab_yaml = new File(df, "prefabs" + File.separator + name + ".yml");
        if (!prefab_yaml.exists()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + Prefix + "] ERROR while loading a Prefab Configuration File!");
            return null;
        }else {
            config = YamlConfiguration.loadConfiguration(prefab_yaml).getConfigurationSection("prefab");
            return config;
        }
    }
    public static String getPrefabName(ConfigurationSection config){
        return config.getString("name");
    }

    public static List<String> getBlocks(ConfigurationSection config){
        List<String> blocks = (List<String>) config.getList("blocks");
        return blocks;
    }
}
