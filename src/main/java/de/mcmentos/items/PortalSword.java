package de.mcmentos.items;

import de.mcmentos.Wolfsprefab;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class PortalSword {
    public static ItemStack createPortalSword(Integer amount) {
        ItemStack item = new ItemStack(Material.STICK, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Portal Sword");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_AQUA + "1 Benutzung");
        lore.add(ChatColor.DARK_RED + "Keine Haftung für Schäden!");
        lore.add(ChatColor.DARK_GRAY + "Größe: 16x15x34!");
        meta.setLore(lore);
        // Custom Item Tags
        NamespacedKey key = new NamespacedKey(Wolfsprefab.getInstance(), "wolf_prefab");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "portal_sword");
        // ---
        item.setItemMeta(meta);
        return item;
    }
}
