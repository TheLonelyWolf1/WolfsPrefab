package de.mcmentos.commands;

import de.mcmentos.Wolfsprefab;
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
import java.util.stream.IntStream;

public class WolfsPrefabCommand implements TabCompleter, CommandExecutor {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    List< String > results = new ArrayList< >();

    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("wpspawn")) {
            if(sender instanceof Player) {
                Bukkit.getConsoleSender().sendMessage(args);
                Player p = (Player) sender;
                //  /wpspawn <Prefab/arg0> <Name/arg1> <Amount/arg2>
                if(args.length <= 1){
                    p.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                    p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.spawnhelp")).replaceAll("%wp-args%", cmd.getName()));
                }else if(args.length == 2){
                    Player player = Bukkit.getPlayer(args[1]);
                    assert player != null;
                    GiveFunctions.givePrefabItem(player, 64, args[0]);
                } else if (args.length == 3) {
                    Player player = Bukkit.getPlayer(args[1]);
                    assert player != null;
                    String amount = args[2];
                    GiveFunctions.givePrefabItem(player, Integer.valueOf(amount), args[0]);
                } else {
                    p.sendMessage(ChatColor.RED + "Falsche Benutzung! Bitte nutze:");
                    p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.spawnhelp")).replaceAll("%wp-args%", cmd.getName()));
                }
            }
        }
        return false;
    }

    @Override
    public List < String > onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            if (cmd.getLabel().equalsIgnoreCase("wpspawn")) {
                results.clear();
                List <String> files = PrefabFileHandler.getPrefabFiles();
                results.addAll(files);
                return sortedResults(args[0]);
            }
        }else if(args.length == 2){
            if (cmd.getLabel().equalsIgnoreCase("wpspawn")) {
                results.clear();
                Collection<? extends Player> Players = Bukkit.getOnlinePlayers();
                for (Player player : Players){
                    results.add(player.getName());
                }
                return sortedResults(args[1]);
            }
        }else if(args.length == 3){
            if (cmd.getLabel().equalsIgnoreCase("wpspawn")) {
                results.clear();
                for (int i = 1; i < 65 ; i++)
                {
                    results.add(String.valueOf(i));
                }
                return sortedResults(args[2]);
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
