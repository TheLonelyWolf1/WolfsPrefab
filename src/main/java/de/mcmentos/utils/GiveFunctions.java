package de.mcmentos.utils;

import de.mcmentos.items.*;
import org.bukkit.entity.Player;

public class GiveFunctions {

    public static void giveDirthouse(Player p, Integer amount) {
        p.getInventory().addItem(Dirthouse.createDirtHouse(amount));
    }
    public static void giveClassichouse(Player p, Integer amount) {
        p.getInventory().addItem(ClassicHouse.createClassicHouse(amount));
    }

    public static void giveSmallCastleHouse(Player p, Integer amount) {
        p.getInventory().addItem(SmallCastleHouse.createSmallCastleHouse(amount));
    }

    public static void givePortalSword(Player p, Integer amount) {
        p.getInventory().addItem(PortalSword.createPortalSword(amount));
    }

    public static void giveSwampHut(Player p, Integer amount) {
        p.getInventory().addItem(SwampHut.createSwampHut(amount));
    }
}
