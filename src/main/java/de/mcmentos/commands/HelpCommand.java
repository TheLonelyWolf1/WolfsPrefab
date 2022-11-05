package de.mcmentos.commands;

import de.mcmentos.Wolfsprefab;
import org.antlr.v4.runtime.misc.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class HelpCommand implements CommandExecutor {

    final org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();

    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("wphelp")) {
            Set<String> Commands = Plugin.getDescription().getCommands().keySet();
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission("wolfprefab.help")) {
                    if(args.length == 0) {
                        p.sendMessage(String.format(ChatColor.AQUA + "------[%s Hilfe]------", Plugin.getDescription().getName()));
                        for(String s : Commands) {
                            if(Plugin.getDescription().getCommands().get(s).get("permission") != null)
                            {
                                String Commandpermission = (String) Plugin.getDescription().getCommands().get(s).get("permission");
                                if(p.hasPermission(Commandpermission)) {
                                    if(Plugin.getDescription().getCommands().get(s).get("description") != null) {
                                        String Commanddesc = (String) Plugin.getDescription().getCommands().get(s).get("description");
                                        p.sendMessage(String.format("§d/%s§a:§7 %s", s ,Commanddesc));
                                    }else {
                                        Bukkit.getConsoleSender().sendMessage(String.format(ChatColor.AQUA + "[%s]§cERROR! 'description' = null! Displaying normal Command", Plugin.getDescription().getName(), s));
                                        Bukkit.getConsoleSender().sendMessage(String.format(ChatColor.AQUA + "[%s]§d/%s ", Plugin.getDescription().getName(), s));
                                    }
                                }
                            }else {
                                Bukkit.getConsoleSender().sendMessage(String.format("§b[%s]§cERROR! 'permission' = null! Displaying normal Command", Plugin.getDescription().getName()));
                                Bukkit.getConsoleSender().sendMessage(String.format("§b[%s]§7/%s ", Plugin.getDescription().getName(), s));
                            }
                        }
                        p.sendMessage("§b----------------------------");
                    } else {
                        p.sendMessage("§cKorrekte Nutzung: /whelp");
                    }
                }else{
                    p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.perm")).replaceAll("%cp-args%", Objects.requireNonNull(cmd.getPermission())));
                }
            }else {
                Bukkit.getConsoleSender().sendMessage(String.format("§b------[%s Hilfe]------", Plugin.getDescription().getName()));
                for(String s : Commands) {
                    if(Plugin.getDescription().getCommands().get(s).get("description") != null) {
                        String Commanddesc = (String) Plugin.getDescription().getCommands().get(s).get("description");
                        Bukkit.getConsoleSender().sendMessage(String.format("§7/%s §a>§7 %s", s ,Commanddesc));
                    }else {
                        Bukkit.getConsoleSender().sendMessage(String.format("§b[%s]§cERROR! 'description' = null! Displaying normal Command", Plugin.getDescription().getName()));
                        Bukkit.getConsoleSender().sendMessage(String.format("§b[%s]§7/%s ", Plugin.getDescription().getName(), s ));
                    }
                }
            }
        }
        return false;
    }
}
