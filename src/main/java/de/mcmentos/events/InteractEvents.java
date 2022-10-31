package de.mcmentos.events;

import de.mcmentos.Wolfsprefab;
import de.mcmentos.utils.CooldownManager;
import de.mcmentos.utils.PrefabBuilder;
import de.mcmentos.utils.PrefabFileHandler;
import de.mcmentos.utils.UtilsFunctions;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class InteractEvents implements Listener {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static String Prefix = Plugin.getName();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        World world = p.getWorld();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && p.getInventory().getItemInMainHand().getType().equals(Material.STICK)){
            if(CooldownManager.checkcooldownPrefab(e.getPlayer())){
                CooldownManager.setcooldownPrefab(e.getPlayer(), 5);
                p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                // Get the direction the player is looking.
                String direction = UtilsFunctions.getPlayerDirection(p);
                if(debug){
                    p.sendMessage(direction);
                }
                //Location block = new Location(world, ClickedBLockLocationX + 0, ClickedBLockLocationY + 1, ClickedBLockLocationZ + 0);
                //world.getBlockAt(block).setType(Material.DIRT);
                //world.playSound(block, Sound.BLOCK_SMITHING_TABLE_USE, 1, 1);


                // ItemStack(Objects.requireNonNull(Material.getMaterial(item.toUpperCase())))
                PrefabBuilder.buildPrefab(world, Objects.requireNonNull(e.getClickedBlock()), direction, new ItemStack(Material.STICK));
            }else {
                p.sendMessage("Du hast noch einen Cooldown von " + ChatColor.GOLD + CooldownManager.getcooldownPrefab(p) + ChatColor.RESET + " Sekunden!");
            }
        }
    }
}
