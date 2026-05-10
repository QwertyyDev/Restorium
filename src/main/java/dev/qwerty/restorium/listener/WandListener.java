package dev.qwerty.restorium.listener;

import dev.qwerty.restorium.Restorium;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WandListener implements Listener {
    
    private final Restorium plugin;
    
    public WandListener(Restorium plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        if (item == null || item.getType() != Material.WOODEN_AXE) {
            return;
        }
        
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
        }
        
        if (!item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Arena Wand")) {
            return;
        }
        
        if (!player.hasPermission("arenarest.wand")) {
            return;
        }
        
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        
        event.setCancelled(true);
        
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            plugin.getRegionManager().setPos1(player, block.getLocation());
            player.sendMessage(ChatColor.GREEN + "Pos1 ayarlandı: " + 
                block.getX() + ", " + block.getY() + ", " + block.getZ());
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            plugin.getRegionManager().setPos2(player, block.getLocation());
            player.sendMessage(ChatColor.GREEN + "Pos2 ayarlandı: " + 
                block.getX() + ", " + block.getY() + ", " + block.getZ());
        }
    }
}