package de.mcmentos.utils;

import de.mcmentos.Wolfsprefab;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrefabFileHandler {

    // Get the instance of the plugin
    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    // Get the status of the debug mode
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();
    // Set the directory of the prefab folder
    public static File prefab_folder = new File(Plugin.getDataFolder(), "prefabs" + File.separator);

    public static void createfolder() {
        // If folder doesn't exist, create one
        if(!prefab_folder.exists()){
            if(debug){
                Bukkit.getConsoleSender().sendMessage("§d[%s]§r Erstelle Ordner für Prefabs.");
            }
            prefab_folder.mkdir();
        }
    }
    // Get all the Files in the Prefab directory
    public static List<String> getPrefabFiles(){
        // Get a list of all entries in the prefab folder
        File[] listOfFilesRAW = prefab_folder.listFiles();
        // Create an empty arraylist
        List<String> listOfFiles = new ArrayList< >();

        // For each entry in the prefab folder
        for (int i = 0; i < Objects.requireNonNull(listOfFilesRAW).length; i++) {
            // If the entry is a file
            if (listOfFilesRAW[i].isFile()) {
                if(debug){
                    Bukkit.getConsoleSender().sendMessage(getNameWithoutExtension(listOfFilesRAW[i].getName()));
                }
                // Add the file to the arraylist
                listOfFiles.add(getNameWithoutExtension(listOfFilesRAW[i].getName()));
            }
        }
        // Return the arraylist containing all files
        return listOfFiles;
    }
    // Get the Filename without the Extension
    public static String getNameWithoutExtension(String file) {
        int dotIndex = file.lastIndexOf('.');
        return (dotIndex == -1) ? file : file.substring(0, dotIndex);
    }
}
