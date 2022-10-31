package de.mcmentos.utils;

import de.mcmentos.items.ClassicHouse;
import de.mcmentos.items.Dirthouse;
import org.bukkit.entity.Player;

public class GiveFunctions {

    public static void giveDirthouse(Player p, Integer amount) {
        p.getInventory().addItem(Dirthouse.createDirtHouse(amount));
    }
    public static void giveClassichouse(Player p, Integer amount) {
        p.getInventory().addItem(ClassicHouse.createClassicHouse(amount));
    }
}
