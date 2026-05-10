package dev.qwerty.restorium.manager;

import dev.qwerty.restorium.Restorium;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleManager {
    
    private final Restorium plugin;
    private BukkitTask task;
    
    @Getter
    private long nextResetTime;
    @Getter
    private long intervalTicks;
    
    public ScheduleManager(Restorium plugin) {
        this.plugin = plugin;
    }
    
    public void start() {
        String schedule = plugin.getConfigManager().getSchedule();
        this.intervalTicks = parseSchedule(schedule);
        this.nextResetTime = System.currentTimeMillis() + (intervalTicks * 50);
        
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            plugin.getArenaManager().resetArena();
            this.nextResetTime = System.currentTimeMillis() + (intervalTicks * 50);
        }, intervalTicks, intervalTicks);
    }
    
    public void stop() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
    
    public void restart() {
        stop();
        start();
    }
    
    public long getTimeUntilReset() {
        return nextResetTime - System.currentTimeMillis();
    }
    
    private long parseSchedule(String schedule) {
        long totalSeconds = 0;
        
        Pattern pattern = Pattern.compile("(\\d+)([smhd])");
        Matcher matcher = pattern.matcher(schedule.toLowerCase());
        
        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);
            
            switch (unit) {
                case "s":
                    totalSeconds += value;
                    break;
                case "m":
                    totalSeconds += value * 60L;
                    break;
                case "h":
                    totalSeconds += value * 3600L;
                    break;
                case "d":
                    totalSeconds += value * 86400L;
                    break;
            }
        }
        
        return totalSeconds * 20;
    }
}