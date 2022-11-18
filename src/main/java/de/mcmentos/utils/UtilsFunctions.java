package de.mcmentos.utils;

import org.bukkit.entity.Player;

public class UtilsFunctions {

    public static String getPlayerDirection(Player p){
        double rotation = p.getLocation().getYaw() - 180;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 45) {
            return "North";
        }
        if (45 <= rotation && rotation < 125) {
            return "East";
        }
        if (125 <= rotation && rotation < 225) {
            return "South";
        }
        if (225 <= rotation && rotation < 315) {
            return "West" ;
        }
        if (315 <= rotation && rotation <= 360) {
            return "North";
        }
        return "ERROR";
    }


}
