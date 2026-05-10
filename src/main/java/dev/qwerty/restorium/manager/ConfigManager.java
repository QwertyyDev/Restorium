package dev.qwerty.restorium.manager;

import dev.qwerty.restorium.Restorium;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class ConfigManager {
    
    private final Restorium plugin;
    private FileConfiguration config;
    
    public ConfigManager(Restorium plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }
    
    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }
    
    public String getSchematic() {
        return config.getString("arena.schematic", "arena");
    }
    
    public Location getPasteLocation() {
        String world = config.getString("arena.location.world", "world");
        int x = config.getInt("arena.location.x", 0);
        int y = config.getInt("arena.location.y", 64);
        int z = config.getInt("arena.location.z", 0);
        return new Location(plugin.getServer().getWorld(world), x, y, z);
    }
    
    public String getSchedule() {
        return config.getString("arena.reset-schedule", "1h");
    }
    
    public boolean isRegionEnabled() {
        return config.getBoolean("arena.region.enabled", true);
    }
    
    public Location getPos1() {
        int x = config.getInt("arena.region.pos1.x", -50);
        int y = config.getInt("arena.region.pos1.y", 0);
        int z = config.getInt("arena.region.pos1.z", -50);
        String world = config.getString("arena.location.world", "world");
        return new Location(plugin.getServer().getWorld(world), x, y, z);
    }
    
    public Location getPos2() {
        int x = config.getInt("arena.region.pos2.x", 50);
        int y = config.getInt("arena.region.pos2.y", 256);
        int z = config.getInt("arena.region.pos2.z", 50);
        String world = config.getString("arena.location.world", "world");
        return new Location(plugin.getServer().getWorld(world), x, y, z);
    }
    
    public boolean isTitleEnabled() {
        return config.getBoolean("arena.title.enabled", true);
    }
    
    public String getClockEmoji() {
        return config.getString("arena.title.clock", "⏰");
    }
    
    public String getResettingMessage() {
        return config.getString("arena.title.resetting", "&eSıfırlanıyor...");
    }
    
    public boolean isSoundEnabled() {
        return config.getBoolean("arena.sound.enabled", true);
    }
    
    public String getSuccessSound() {
        return config.getString("arena.sound.success", "ENTITY_PLAYER_LEVELUP");
    }
    
    public float getSoundVolume() {
        return (float) config.getDouble("arena.sound.volume", 1.0);
    }
    
    public float getSoundPitch() {
        return (float) config.getDouble("arena.sound.pitch", 1.0);
    }
}