package de.mcmentos.commands;

import de.mcmentos.Wolfsprefab;
import de.mcmentos.utils.CommandRegisterer;
import de.mcmentos.utils.GiveFunctions;
import de.mcmentos.utils.PrefabFileHandler;
import org.antlr.v4.runtime.misc.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
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
                Player p = (Player) sender;
                //  /wp <command/args0> <Prefab/arg1> <Name/arg2> <Amount/arg3>
                if(args.length == 0){
                    p.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                    p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                }else if(args.length == 1){
                    // /wp spawn
                    switch (args[0]) {
                        case "prefablist":
                            p.sendMessage(ChatColor.GRAY + "-=" + ChatColor.GREEN + Plugin.getName() + ChatColor.GRAY + "=-");
                            p.sendMessage(ChatColor.GRAY + "Bilder zu den Prefabs gibt es hier:");
                            p.sendMessage(ChatColor.AQUA + "https://github.com/TheLonelyWolf1/WolfsPrefab/tree/master/src/main/images");
                            p.sendMessage(ChatColor.GRAY + "Eine Liste zu den Prefabs gibt es hier:");
                            p.sendMessage(ChatColor.AQUA + "https://github.com/TheLonelyWolf1/WolfsPrefab/tree/master/src/main/schematics");
                            break;
                        case "give":
                            p.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                            p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.spawn")).replaceAll("%wp-args%", cmd.getName()));
                            break;
                        case "info":
                            p.sendMessage(ChatColor.GRAY + "-=" + ChatColor.GREEN + Plugin.getName() + ChatColor.GRAY + "=-");
                            p.sendMessage(ChatColor.AQUA + "Version: " + ChatColor.GRAY + Plugin.getDescription().getVersion());
                            p.sendMessage(ChatColor.AQUA + "Author: " + ChatColor.GRAY + Plugin.getDescription().getAuthors());
                            p.sendMessage(ChatColor.AQUA + "Command: " + ChatColor.GRAY + "/wp");
                            break;
                        case "help":
                            p.sendMessage(String.format(ChatColor.AQUA + "------[%s Hilfe]------", Plugin.getDescription().getName()));
                            List<String> subcommands = CommandRegisterer.Subcommands();
                            for (String subcommand : subcommands) {
                                if (subcommand.equals("help")) {
                                    p.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Zeigt dieses Hilfemenü");
                                } else if (subcommand.equals("give")) {
                                    p.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Spawnbefehl für die Prefabs");
                                } else if (subcommand.equals("info")) {
                                    p.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " Plugin Informationen");
                                } else {
                                    p.sendMessage(ChatColor.GREEN + "/wp " + subcommand + ChatColor.GRAY + ChatColor.ITALIC.toString() + " None");
                                }
                            }
                            p.sendMessage("§b----------------------------");
                            break;
                        default:
                            p.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                            p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                            break;
                    }
                }else if(args.length == 2){
                    // /wp spawn <prefab>
                    if(args[0].equals("give")){
                        GiveFunctions.givePrefabItem(p, 64, args[1]);
                    }
                }else if(args.length == 3){
                    // /wp spawn <prefab> <Name>
                    if(args[0].equals("give")){
                        Player player = Bukkit.getPlayer(args[2]);
                        assert player != null;
                        GiveFunctions.givePrefabItem(player, 64, args[1]);
                    }
                } else if (args.length == 4) {
                    // /wp spawn <prefab> <Name> <Amount>
                    if(args[0].equals("give")){
                        Player player = Bukkit.getPlayer(args[2]);
                        assert player != null;
                        String amount = args[3];
                        GiveFunctions.givePrefabItem(player, Integer.valueOf(amount), args[1]);
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                    p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.help.default")).replaceAll("%wp-args%", cmd.getName()));
                }
            }else{
                Bukkit.getConsoleSender().sendMessage("[" + Plugin.getName() + "] This command is player-only!");
            }
        }
        return false;
    }

    @Override
    public List < String > onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        // /wp <command/args0> <Prefab/arg1> <Name/arg2> <Amount/arg3>
        if (args.length == 1) {
            if (cmd.getLabel().equalsIgnoreCase("wp")) {
                results.clear();
                List <String> subcommands = CommandRegisterer.Subcommands();
                for (String subcommand : subcommands){
                    results.add(subcommand);
                }
                return sortedResults(args[0]);
            }
        }else if (args.length == 2) {
            if (cmd.getLabel().equalsIgnoreCase("wp") && args[0].equals("give")) {
                results.clear();
                List <String> files = PrefabFileHandler.getPrefabFiles();
                results.addAll(files);
                return sortedResults(args[1]);
            }
        }else if(args.length == 3){
            if (cmd.getLabel().equalsIgnoreCase("wp") && args[0].equals("give")) {
                results.clear();
                Collection<? extends Player> Players = Bukkit.getOnlinePlayers();
                for (Player player : Players){
                    results.add(player.getName());
                }
                return sortedResults(args[2]);
            }
        }else if(args.length == 4){
            if (cmd.getLabel().equalsIgnoreCase("wp") && args[0].equals("give")) {
                results.clear();
                for (int i = 1; i < 65 ; i++)
                {
                    results.add(String.valueOf(i));
                }
                return sortedResults(args[3]);
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
