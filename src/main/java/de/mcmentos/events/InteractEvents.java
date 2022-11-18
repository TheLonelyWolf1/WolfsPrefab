package de.mcmentos.events;

import com.sk89q.worldedit.WorldEditException;
import de.mcmentos.Wolfsprefab;
import de.mcmentos.utils.CooldownManager;
import de.mcmentos.utils.PrefabBuilder;
import de.mcmentos.utils.UtilsFunctions;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class InteractEvents implements Listener {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static String Prefix = Plugin.getName();
    static Boolean debug = Wolfsprefab.instance.isDebugEnabled();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) throws IOException, WorldEditException {
        // Get Player from the Event
        Player p = e.getPlayer();
        // Get World from the Event
        World world = p.getWorld();
        // Create a NamespacedKey for use in the function
        NamespacedKey key = new NamespacedKey(Plugin, "wolf_prefab");
        // Get the ItemMeta from the Item in the Mainhand of the Player
        ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
        // If the Item has no Meta set, return
        if(meta == null){
            return;
        }
        // Get the Worlds from the config_path
        List<String> worlds = (List<String>) Plugin.getConfig().getList("items.worlds");
        // If there is no config_path or the list is empty, return
        if(worlds == null){
            return;
        }
        // If the World from the Event isn't in the List from the config
        else if(!worlds.contains(world.getName())){
            p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.disabledworld")));
            return;
        }
        // Get the Persistant Data from the Itemmeta
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(!container.has(key, PersistentDataType.STRING)){
            return;
        }

        // If Player rightclicked with the item and the held item is a Stick
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && p.getInventory().getItemInMainHand().getType().equals(Material.STICK)){
            //Check if the Player has a Cooldown
            if(!CooldownManager.checkcooldownPrefab(e.getPlayer())){
                p.sendMessage(Objects.requireNonNull(Plugin.getConfig().getString("format.cooldown")).replaceAll("%wp-args%", String.valueOf(CooldownManager.getcooldownPrefab(p))));
                return;
            }
            // Get the Prefab-Name from the PersistentDataContainer
            String PrefabName = container.get(key, PersistentDataType.STRING);
            // Set a Cooldown of 1 Seconds for the player
            CooldownManager.setcooldownPrefab(e.getPlayer(), 1);
            // Remove the Stick from the Player's Inventory
            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
            // Get the direction the player is looking.
            String direction = UtilsFunctions.getPlayerDirection(p);
            if(debug){
                p.sendMessage(direction);
                assert PrefabName != null;
                p.sendMessage(PrefabName);
            }
            // Load the Prefab at the desired location in the desired direction
            PrefabBuilder.loadSchematic(p,PrefabName, Objects.requireNonNull(e.getClickedBlock()).getLocation(), direction);
        }
    }
}
