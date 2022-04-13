package vinniemaen.customenchantsbmc.customenchantsbmc.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class enchantmentListInventories implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() != null) {
            if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lAvailable Custom Enchants" + ChatColor.DARK_AQUA + "]")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Sword Enchantments")) {
                    Inventory gui = Bukkit.createInventory(player, 54, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lAvailable Custom Enchants" + ChatColor.DARK_AQUA + "]");


                }

            }
        }
    }
}
