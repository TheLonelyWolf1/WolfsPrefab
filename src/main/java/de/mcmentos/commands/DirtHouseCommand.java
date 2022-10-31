package de.mcmentos.commands;

import de.mcmentos.Wolfsprefab;
import de.mcmentos.utils.GiveFunctions;
import org.antlr.v4.runtime.misc.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DirtHouseCommand implements CommandExecutor {

    org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();

    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("wpdirthouse")) {
            if(sender instanceof Player) {
                Player p = (Player)sender;
                if(p.hasPermission("wolfprefab.admin.give")) {
                    if(args.length == 0) {
                        GiveFunctions.giveDirthouse(p, 64);
                    }else if(args.length == 1){
                        String amount = args[1];
                        GiveFunctions.giveDirthouse(p, Integer.parseInt(amount));
                    }else if(args.length == 2){
                        Player other_player = Bukkit.getPlayer(args[0]);
                        assert other_player != null;
                        String amount = args[1];
                        GiveFunctions.giveDirthouse(other_player, Integer.parseInt(amount));
                    }else{
                        Bukkit.getConsoleSender().sendMessage(String.format("§e/%s <Name> <Anzahl>", cmd.getName()));
                    }
                }
            }else{
                if(args.length == 0){
                    Bukkit.getConsoleSender().sendMessage(String.format("§e/%s <Name> <Anzahl>", cmd.getName()));
                }else if(args.length == 1){
                    Player p = Bukkit.getPlayer(args[0]);
                    assert p != null;
                    GiveFunctions.giveDirthouse(p, 64);
                }else if(args.length == 2){
                    Player p = Bukkit.getPlayer(args[0]);
                    assert p != null;
                    String amount = args[1];
                    GiveFunctions.giveDirthouse(p, Integer.parseInt(amount));
                }else{
                    Bukkit.getConsoleSender().sendMessage(String.format("§e/%s <Name> <Anzahl>", cmd.getName()));
                }
            }
        }
        return false;
    }
}
