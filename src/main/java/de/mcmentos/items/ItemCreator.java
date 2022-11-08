package de.mcmentos.items;

import de.mcmentos.Wolfsprefab;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ItemCreator {

    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();

    public static ItemStack createPrefabItem(String prefab, Integer amount) {
        String[] prefab_args = prefab.split("-");
        String prefab_without_underscore = prefab_args[0].replaceAll("_", " ");
        if(debug){
            Bukkit.getConsoleSender().sendMessage(prefab_args);
            Bukkit.getConsoleSender().sendMessage("--");
            Bukkit.getConsoleSender().sendMessage(prefab_without_underscore + "/" + prefab_args[0] + "/" + prefab_args[1]);
        }
        ItemStack item = new ItemStack(Material.STICK, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + WordUtils.capitalizeFully(prefab_without_underscore));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_AQUA + "1 Benutzung");
        lore.add(ChatColor.DARK_GRAY + "Größe: " + ChatColor.AQUA + prefab_args[1]);
        lore.add(ChatColor.RED + ChatColor.ITALIC.toString() + "Keine Haftung für Schäden!");
        meta.setLore(lore);
        // Custom Item Tags
        NamespacedKey key = new NamespacedKey(Wolfsprefab.getInstance(), "wolf_prefab");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, prefab);
        // ---
        item.setItemMeta(meta);
        return item;
    }
}
