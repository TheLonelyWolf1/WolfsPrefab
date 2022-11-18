package de.mcmentos.commands;

import de.mcmentos.Wolfsprefab;
import de.mcmentos.utils.CommandRegisterer;
import de.mcmentos.utils.GiveFunctions;
import de.mcmentos.utils.PrefabFileHandler;
import org.antlr.v4.runtime.misc.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class WolfsPrefabCommand implements TabCompleter, CommandExecutor {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();
    List< String > results = new ArrayList< >();

    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("wp")) {
            if(sender instanceof Player) {
                if(debug){
                    Bukkit.getConsoleSender().sendMessage(args);
                }
                Player player = (Player) sender;
                //If Player doesn't have the permission
                if(!player.hasPermission("wolfprefab.admin.give")){
                    player.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.perm")).replaceAll("%wp-args%", "wolfprefab.admin.give"));
                    return false;
                }
                //  /wp <command/args0> <Prefab/arg1> <Name/arg2> <Amount/arg3>
                switch(args.length){
                    // If no sub-command has been given
                    case 0:
                        player.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                        player.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                        break;
                    // If only a sub-command has been given
                    case 1:
                        switch (args[0]) {
                            // If sub-command is prefablist
                            case "prefablist":
                                player.sendMessage(ChatColor.GRAY + "-=" + ChatColor.GREEN + Plugin.getName() + ChatColor.GRAY + "=-");
                                player.sendMessage(ChatColor.GRAY + "Bilder zu den Prefabs gibt es hier:");
                                player.sendMessage(ChatColor.AQUA + "https://github.com/TheLonelyWolf1/WolfsPrefab/tree/master/src/main/images");
                                player.sendMessage(ChatColor.GRAY + "Eine Liste zu den Prefabs gibt es hier:");
                                player.sendMessage(ChatColor.AQUA + "https://github.com/TheLonelyWolf1/WolfsPrefab/tree/master/src/main/schematics");
                                break;
                            // If sub-command is give
                            case "give":
                                player.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                                player.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.spawn")).replaceAll("%wp-args%", cmd.getName()));
                                break;
                            // If sub-command is info
                            case "info":
                                player.sendMessage(ChatColor.GRAY + "-=" + ChatColor.GREEN + Plugin.getName() + ChatColor.GRAY + "=-");
                                player.sendMessage(ChatColor.AQUA + "Version: " + ChatColor.GRAY + Plugin.getDescription().getVersion());
                                player.sendMessage(ChatColor.AQUA + "Author: " + ChatColor.GRAY + Plugin.getDescription().getAuthors());
                                player.sendMessage(ChatColor.AQUA + "Command: " + ChatColor.GRAY + "/wp");
                                break;
                            // If sub-command is help
                            case "help":
                                player.sendMessage(String.format(ChatColor.AQUA + "------[%s Hilfe]------", Plugin.getDescription().getName()));
                                // Get all sub-commands in a List
                                List<String> subcommands = CommandRegisterer.Subcommands();
                                // For each subcommand send a message containing a description to the command
                                for (String subcommand : subcommands) {
                                    switch(subcommand){
                                        case "help":
                                            player.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Zeigt dieses Hilfemenü");
                                            break;
                                        case "give":
                                            player.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Spawnbefehl für die Prefabs");
                                            break;
                                        case "info":
                                            player.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Plugin Informationen");
                                            break;
                                        default:
                                            player.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " None");
                                            break;
                                    }
                                }
                                player.sendMessage("§b----------------------------");
                                break;
                            // If the sub-command is something other than the previous cases
                            default:
                                player.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                                player.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                                break;
                        }
                        break;
                    // If one additional argument is given
                    case 2:
                        // If the sub-command is give
                        if(args[0].equals("give")){
                            // Give the Player 64 items for the second argument as the prefab name
                            GiveFunctions.givePrefabItem(player, 64, args[1]);
                        }
                        break;
                    // If two additional arguments are given
                    case 3:
                        // If the sub-command is give
                        if(args[0].equals("give")){
                            // Get the Player from the second additional argument
                            Player other_player = Bukkit.getPlayer(args[2]);
                            // Check if other player has been found
                            assert other_player != null;
                            // Give the Player 64 item for the first additional argument as the prefab name
                            GiveFunctions.givePrefabItem(other_player, 64, args[1]);
                        }
                        break;
                    // If three additional arguments are given
                    case 4:
                        // If the sub-command is give
                        if(args[0].equals("give")){
                            // Get the Player from the second additional argument
                            Player other_player = Bukkit.getPlayer(args[2]);
                            // Check if other player has been found
                            assert other_player != null;
                            String amount = args[3];
                            // Give the Player the amount of items given by the third additional argument and the first additional argument as the prefab name
                            GiveFunctions.givePrefabItem(other_player, Integer.valueOf(amount), args[1]);
                        }
                        break;
                    // If none of the previous cases apply
                    default:
                        player.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                        player.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                        break;
                }
            }else{ // Assume that it's the console which executes the command
                ConsoleCommandSender console = Bukkit.getConsoleSender();
                // Ff there is no sub-command
                if(args.length == 0){
                    console.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                    console.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                    return false;
                }
                switch (args.length){
                    // Ff there is only the sub-command
                    case 1:
                        switch (args[0]) {
                            // If the sub-command is prefablist
                            case "prefablist":
                                console.sendMessage(ChatColor.GRAY + "-=" + ChatColor.GREEN + Plugin.getName() + ChatColor.GRAY + "=-");
                                console.sendMessage(ChatColor.GRAY + "Bilder zu den Prefabs gibt es hier:");
                                console.sendMessage(ChatColor.AQUA + "https://github.com/TheLonelyWolf1/WolfsPrefab/tree/master/src/main/images");
                                console.sendMessage(ChatColor.GRAY + "Eine Liste zu den Prefabs gibt es hier:");
                                console.sendMessage(ChatColor.AQUA + "https://github.com/TheLonelyWolf1/WolfsPrefab/tree/master/src/main/schematics");
                                break;
                            // If the sub-command is give
                            case "give":
                                console.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                                console.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.spawn")).replaceAll("%wp-args%", cmd.getName()));
                                break;
                            // If the sub-command is info
                            case "info":
                                console.sendMessage(ChatColor.GRAY + "-=" + ChatColor.GREEN + Plugin.getName() + ChatColor.GRAY + "=-");
                                console.sendMessage(ChatColor.AQUA + "Version: " + ChatColor.GRAY + Plugin.getDescription().getVersion());
                                console.sendMessage(ChatColor.AQUA + "Author: " + ChatColor.GRAY + Plugin.getDescription().getAuthors());
                                console.sendMessage(ChatColor.AQUA + "Command: " + ChatColor.GRAY + "/wp");
                                break;
                            // If the sub-command is help
                            case "help":
                                console.sendMessage(String.format(ChatColor.AQUA + "------[%s Hilfe]------", Plugin.getDescription().getName()));
                                // Get all sub-commands in a List
                                List<String> subcommands = CommandRegisterer.Subcommands();
                                // For each subcommand send a message containing a description to the command
                                for (String subcommand : subcommands) {
                                    switch(subcommand){
                                        case "help":
                                            console.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Zeigt dieses Hilfemenü");
                                            break;
                                        case "give":
                                            console.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Spawnbefehl für die Prefabs");
                                            break;
                                        case "info":
                                            console.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Plugin Informationen");
                                            break;
                                        default:
                                            console.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " None");
                                            break;
                                    }
                                }
                                console.sendMessage("§b----------------------------");
                                break;
                            // If the sub-command is something other than the previous cases
                            default:
                                console.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                                console.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                                break;
                        }
                        break;
                    // case 2 and 3 are the same
                    case 2:
                    case 3:
                        // /wp spawn <prefab> <Name>
                        if(args[0].equals("give")){
                            console.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                            console.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.spawn")).replaceAll("%wp-args%", cmd.getName()));
                        }
                        break;
                    case 4:
                        // /wp spawn <prefab> <Name> <Amount>
                        if(args[0].equals("give")){
                            Player player = Bukkit.getPlayer(args[2]);
                            assert player != null;
                            String amount = args[3];
                            GiveFunctions.givePrefabItem(player, Integer.valueOf(amount), args[1]);
                        }
                        break;
                    default:
                        console.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                        console.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                        break;
                }
            }
        }
        return false;
    }

    @Override
    public List < String > onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        // /wp <command/args0> <Prefab/arg1> <Name/arg2> <Amount/arg3>
        if(cmd.getLabel().equalsIgnoreCase("wp")){
            results.clear();
            switch(args.length){
                case 1: // if the first argument needs to completed
                    List <String> subcommands = CommandRegisterer.Subcommands();
                    results.addAll(subcommands);
                    return sortedResults(args[0]);
                case 2: // if the second argument needs to completed
                    if (args[0].equals("give")) {
                        List <String> files = PrefabFileHandler.getPrefabFiles();
                        results.addAll(files);
                        return sortedResults(args[1]);
                    }
                    break;
                case 3: // if the third argument needs to completed
                    if (args[0].equals("give")) {
                        Collection<? extends Player> Players = Bukkit.getOnlinePlayers();
                        for (Player player : Players){
                            results.add(player.getName());
                        }
                        return sortedResults(args[2]);
                    }
                    break;
                case 4: // if the fourth argument needs to completed
                    if (args[0].equals("give")) {
                        for (int i = 1; i < 65 ; i++)
                        {
                            results.add(String.valueOf(i));
                        }
                        return sortedResults(args[3]);
                    }
                    break;
                default: // if none of the previous cases apply, return
                    break;
            }
        }
        return sortedResults(args[0]);
    }

    // Sorts possible results to provide true tab auto complete based off of what is already typed.
    public List <String> sortedResults(String arg) {
        final List <String> completions = new ArrayList < > ();
        StringUtil.copyPartialMatches(arg, results, completions);
        Collections.sort(completions);
        results.clear();
        results.addAll(completions);
        return results;
    }
}
