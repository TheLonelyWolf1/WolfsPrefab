package de.mcmentos.utils;

import de.mcmentos.Wolfsprefab;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrefabFileHandler {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();
    public static File prefab_folder = new File(Plugin.getDataFolder(), "prefabs" + File.separator);
    static File df = Plugin.getDataFolder();

    public static void createfolder() {
        if(!prefab_folder.exists()){
            if(debug){
                Bukkit.getConsoleSender().sendMessage("§d[%s]§r Erstelle Ordner für Prefabs.");
            }
            prefab_folder.mkdir();
        }
    }
    // Get all the Files in the Prefab directory
    public static List<String> getPrefabFiles(){
        File[] listOfFilesRAW = prefab_folder.listFiles();
        List<String> listOfFiles = new ArrayList< >();

        for (int i = 0; i < Objects.requireNonNull(listOfFilesRAW).length; i++) {
            if (listOfFilesRAW[i].isFile()) {
                if(debug){
                    Bukkit.getConsoleSender().sendMessage(getNameWithoutExtension(listOfFilesRAW[i].getName()));
                }
                listOfFiles.add(getNameWithoutExtension(listOfFilesRAW[i].getName()));
            }
        }
        return listOfFiles;
    }
    // Get the Filename without the Extension
    public static String getNameWithoutExtension(String file) {
        int dotIndex = file.lastIndexOf('.');
        return (dotIndex == -1) ? file : file.substring(0, dotIndex);
    }
}
