package de.mcmentos.utils;

import de.mcmentos.items.*;
import org.bukkit.entity.Player;

public class GiveFunctions {

    public static void givePrefabItem(Player p, Integer amount, String prefab){
        p.getInventory().addItem(ItemCreator.createPrefabItem(prefab, amount));
    }
}
