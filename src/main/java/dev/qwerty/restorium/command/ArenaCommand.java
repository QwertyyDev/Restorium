package dev.qwerty.restorium.command;

import dev.qwerty.restorium.Restorium;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ArenaCommand implements CommandExecutor {
    
    private final Restorium plugin;
    
    public ArenaCommand(Restorium plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Kullanım: /restorium <reload|wand>");
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                if (!sender.hasPermission("restorium.reload")) {
                    sender.sendMessage(ChatColor.RED + "Yetkiniz yok!");
                    return true;
                }
                plugin.reload();
                sender.sendMessage(ChatColor.GREEN + "Plugin yeniden yüklendi!");
                return true;
                
            case "wand":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Bu komut sadece oyuncular için!");
                    return true;
                }
                
                if (!sender.hasPermission("restorium.wand")) {
                    sender.sendMessage(ChatColor.RED + "Yetkiniz yok!");
                    return true;
                }
                
                Player player = (Player) sender;
                ItemStack wand = new ItemStack(Material.WOODEN_AXE);
                ItemMeta meta = wand.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + "Arena Wand");
                meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Sol tık: Pos1",
                    ChatColor.GRAY + "Sağ tık: Pos2"
                ));
                wand.setItemMeta(meta);
                
                player.getInventory().addItem(wand);
                player.sendMessage(ChatColor.GREEN + "Wand verildi!");
                return true;
                
            default:
                sender.sendMessage(ChatColor.RED + "Kullanım: /restorium <reload|wand>");
                return true;
        }
    }
}