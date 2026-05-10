package dev.qwerty.restorium.placeholder;

import dev.qwerty.restorium.Restorium;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class ArenaPlaceholder extends PlaceholderExpansion {
    
    private final Restorium plugin;
    
    public ArenaPlaceholder(Restorium plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getIdentifier() {
        return "restorium";
    }
    
    @Override
    public String getAuthor() {
        return "Qwertydev";
    }
    
    @Override
    public String getVersion() {
        return "1.0.0";
    }
    
    @Override
    public boolean persist() {
        return true;
    }
    
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (!identifier.equalsIgnoreCase("countdown")) {
            return null;
        }
        
        long timeUntilReset = plugin.getScheduleManager().getTimeUntilReset();
        
        if (timeUntilReset < 0) {
            return "Sıfırlanıyor...";
        }
        
        long totalSeconds = timeUntilReset / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        
        if (hours > 0) {
            return hours + " saat";
        } else if (minutes > 0) {
            return minutes + " dakika";
        } else {
            return seconds + " saniye";
        }
    }
}