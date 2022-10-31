package de.mcmentos.events;

import com.sk89q.worldedit.WorldEditException;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class InteractEvents implements Listener {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static String Prefix = Plugin.getName();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) throws IOException, WorldEditException {
        Player p = e.getPlayer();
        World world = p.getWorld();
        NamespacedKey key = new NamespacedKey(Plugin, "wolf_prefab");
        ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
        if(!(meta == null)) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && p.getInventory().getItemInMainHand().getType().equals(Material.STICK) && container.has(key, PersistentDataType.STRING)){
                if(CooldownManager.checkcooldownPrefab(e.getPlayer())){
                    // Get the Name from the PersistentDataContainer
                    String PrefabName = container.get(key, PersistentDataType.STRING);
                    CooldownManager.setcooldownPrefab(e.getPlayer(), 5);
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    // Get the direction the player is looking.
                    String direction = UtilsFunctions.getPlayerDirection(p);
                    if(debug){
                        p.sendMessage(direction);
                        assert PrefabName != null;
                        p.sendMessage(PrefabName);
                    }
                    PrefabBuilder.loadSchematic(p,PrefabName, Objects.requireNonNull(e.getClickedBlock()).getLocation(), direction);
                }else {
                    p.sendMessage("Du hast noch einen Cooldown von " + ChatColor.GOLD + CooldownManager.getcooldownPrefab(p) + ChatColor.RESET + " Sekunden!");
                }
            }
        }
    }
}
