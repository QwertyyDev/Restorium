package dev.qwerty.restorium.manager;

import dev.qwerty.restorium.Restorium;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class RegionManager {
    
    private final Restorium plugin;
    private final Map<UUID, Location> selections = new HashMap<>();
    
    @Setter
    private Location pos1;
    @Setter
    private Location pos2;
    
    public RegionManager(Restorium plugin) {
        this.plugin = plugin;
        loadRegionFromConfig();
    }
    
    public void loadRegionFromConfig() {
        ConfigManager config = plugin.getConfigManager();
        this.pos1 = config.getPos1();
        this.pos2 = config.getPos2();
    }
    
    public void setPos1(Player player, Location location) {
        selections.put(player.getUniqueId(), location);
        this.pos1 = location;
    }
    
    public void setPos2(Player player, Location location) {
        this.pos2 = location;
        saveRegionToConfig(player);
    }
    
    public Location getPlayerPos1(UUID uuid) {
        return selections.get(uuid);
    }
    
    private void saveRegionToConfig(Player player) {
        if (pos1 == null || pos2 == null) {
            return;
        }
        
        plugin.getConfig().set("arena.region.pos1.x", pos1.getBlockX());
        plugin.getConfig().set("arena.region.pos1.y", pos1.getBlockY());
        plugin.getConfig().set("arena.region.pos1.z", pos1.getBlockZ());
        
        plugin.getConfig().set("arena.region.pos2.x", pos2.getBlockX());
        plugin.getConfig().set("arena.region.pos2.y", pos2.getBlockY());
        plugin.getConfig().set("arena.region.pos2.z", pos2.getBlockZ());
        
        plugin.saveConfig();
    }
    
    public void teleportPlayersInRegion() {
        if (pos1 == null || pos2 == null) {
            return;
        }
        
        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location loc = player.getLocation();
            
            if (!loc.getWorld().equals(pos1.getWorld())) {
                continue;
            }
            
            if (loc.getBlockX() >= minX && loc.getBlockX() <= maxX &&
                loc.getBlockY() >= minY && loc.getBlockY() <= maxY &&
                loc.getBlockZ() >= minZ && loc.getBlockZ() <= maxZ) {
                
                Bukkit.getScheduler().runTask(plugin, () -> {
                    player.performCommand("spawn");
                });
            }
        }
    }
}