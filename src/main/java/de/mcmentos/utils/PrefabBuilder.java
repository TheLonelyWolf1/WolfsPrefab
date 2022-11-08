package de.mcmentos.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.mcmentos.Wolfsprefab;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PrefabBuilder {

    static org.bukkit.plugin.Plugin Plugin = Wolfsprefab.getInstance();
    static File df = Plugin.getDataFolder();
    static File prefab_schematic;

    public static void loadSchematic(Player p, String name, Location Loc, String Direction) throws IOException, WorldEditException {
        World world = p.getWorld();
        Clipboard clipboard;
        prefab_schematic = new File(df, "prefabs" + File.separator + name + ".schem");
        ClipboardFormat format = ClipboardFormats.findByFile(prefab_schematic);
        assert format != null;
        try (ClipboardReader reader = format.getReader(Files.newInputStream(prefab_schematic.toPath()))) {
            clipboard = reader.read();
        }
        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(world), -1)) {
            ClipboardHolder holder = new ClipboardHolder(clipboard);
            AffineTransform transform = new AffineTransform();
            switch (Direction) {
                case "East":
                    transform = transform.rotateY(90);
                    transform = transform.rotateX(0);
                    transform = transform.rotateZ(0);
                    holder.setTransform(holder.getTransform().combine(transform));
                    break;
                case "North":
                    transform = transform.rotateY(180);
                    transform = transform.rotateX(0);
                    transform = transform.rotateZ(0);
                    holder.setTransform(holder.getTransform().combine(transform));
                    break;
                case "West":
                    transform = transform.rotateY(270);
                    transform = transform.rotateX(0);
                    transform = transform.rotateZ(0);
                    holder.setTransform(holder.getTransform().combine(transform));
                    break;
            }
            Operation operation = holder
                    .createPaste(editSession)
                    .to(BlockVector3.at(Loc.getX(), Loc.getY(), Loc.getZ()))
                    .ignoreAirBlocks(false)
                    .build();
            Operations.complete(operation);
        }
    }
}
