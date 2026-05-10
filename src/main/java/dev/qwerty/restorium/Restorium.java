package dev.qwerty.restorium;

import dev.qwerty.restorium.command.ArenaCommand;
import dev.qwerty.restorium.listener.WandListener;
import dev.qwerty.restorium.manager.ArenaManager;
import dev.qwerty.restorium.manager.ConfigManager;
import dev.qwerty.restorium.manager.RegionManager;
import dev.qwerty.restorium.manager.ScheduleManager;
import dev.qwerty.restorium.placeholder.ArenaPlaceholder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Restorium extends JavaPlugin {
    
    private ConfigManager configManager;
    private ArenaManager arenaManager;
    private RegionManager regionManager;
    private ScheduleManager scheduleManager;
    
    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.arenaManager = new ArenaManager(this);
        this.regionManager = new RegionManager(this);
        this.scheduleManager = new ScheduleManager(this);
        
        getCommand("restorium").setExecutor(new ArenaCommand(this));
        Bukkit.getPluginManager().registerEvents(new WandListener(this), this);
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ArenaPlaceholder(this).register();
        }
        
        scheduleManager.start();
    }
    
    @Override
    public void onDisable() {
        if (scheduleManager != null) {
            scheduleManager.stop();
        }
    }
    
    public void reload() {
        configManager.reload();
        scheduleManager.restart();
    }
}