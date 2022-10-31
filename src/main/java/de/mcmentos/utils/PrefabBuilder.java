package de.mcmentos.utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PrefabBuilder {

    public static void buildPrefab(World world, Block clicked_Block, String PlayerDirection, ItemStack item){
        double ClickedBLockLocationX = clicked_Block.getLocation().getX();
        double ClickedBLockLocationY = clicked_Block.getLocation().getY();
        double ClickedBLockLocationZ = clicked_Block.getLocation().getZ();

        ConfigurationSection prefab = PrefabFileHandler.loadPrefabYaml("dirthouse");
        //String Prefabname = PrefabFileHandler.getPrefabName(prefab);
        //Bukkit.getConsoleSender().sendMessage(prefab.getCurrentPath() + Prefabname);

        world.playSound(clicked_Block.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE,1 ,1);
        List<String> blocks = PrefabFileHandler.getBlocks(prefab);
        for(String block : blocks){
            // - X Y Z Material Direction
            String[] block_args = block.split(" ");
            Bukkit.getConsoleSender().sendMessage(String.valueOf(block_args.length));
            Bukkit.getConsoleSender().sendMessage(block_args[0] + block_args[1] + block_args[2] + block_args[3] + block_args[4]);
            Bukkit.getConsoleSender().sendMessage(clicked_Block.getLocation().toString());
            Location prefab_block = new Location(world, ClickedBLockLocationX + Double.parseDouble(block_args[0]), ClickedBLockLocationY + Double.parseDouble(block_args[1]),ClickedBLockLocationZ + Double.parseDouble(block_args[2]));
            Bukkit.getConsoleSender().sendMessage(prefab_block.toString());
            world.getBlockAt(prefab_block).setType(Material.valueOf(block_args[3].toUpperCase()));
        }
    }
}
