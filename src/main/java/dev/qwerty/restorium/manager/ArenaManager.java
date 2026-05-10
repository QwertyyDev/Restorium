package dev.qwerty.restorium.manager;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import dev.qwerty.restorium.Restorium;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;

public class ArenaManager {
    
    private final Restorium plugin;
    
    public ArenaManager(Restorium plugin) {
        this.plugin = plugin;
    }
    
    public void resetArena() {
        ConfigManager config = plugin.getConfigManager();
        
        if (config.isTitleEnabled()) {
            String message = ChatColor.translateAlternateColorCodes('&', config.getResettingMessage());
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(config.getClockEmoji(), message, 10, 40, 10);
            }
        }
        
        if (config.isRegionEnabled()) {
            plugin.getRegionManager().teleportPlayersInRegion();
        }
        
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                pasteSchematic(config.getSchematic(), config.getPasteLocation());
                
                Bukkit.getScheduler().runTask(plugin, () -> {
                    if (config.isSoundEnabled()) {
                        playSuccessSound();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    private void pasteSchematic(String schematicName, Location location) throws Exception {
        File schematicFile = new File(WorldEdit.getInstance().getConfiguration().getWorkingDirectory(), 
            "schematics" + File.separator + schematicName + ".schem");
        
        if (!schematicFile.exists()) {
            schematicFile = new File(WorldEdit.getInstance().getConfiguration().getWorkingDirectory(), 
                "schematics" + File.separator + schematicName + ".schematic");
        }
        
        if (!schematicFile.exists()) {
            return;
        }
        
        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
        if (format == null) {
            return;
        }
        
        try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
            Clipboard clipboard = reader.read();
            
            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(location.getWorld());
            BlockVector3 pastePos = BlockVector3.at(location.getX(), location.getY(), location.getZ());
            
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
                Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(pastePos)
                    .ignoreAirBlocks(false)
                    .build();
                
                Operations.complete(operation);
            }
        }
    }
    
    private void playSuccessSound() {
        ConfigManager config = plugin.getConfigManager();
        try {
            Sound sound = Sound.valueOf(config.getSuccessSound());
            float volume = config.getSoundVolume();
            float pitch = config.getSoundPitch();
            
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}