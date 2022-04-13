package vinniemaen.nametags.bmc.bmcnametags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Events implements Listener {

    String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Bucket" + ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA + "]" + " ";

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack handitem = player.getInventory().getItemInMainHand();
        if (e.getItem() != null) {
            if (handitem.getItemMeta() != null) {
                if (handitem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Prefix Nametag")) {
                    List<String> lore = handitem.getItemMeta().getLore();
                    if (handitem.getAmount() == 1) {
                        String tagprefix = lore.get(2);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta removeprefix 0 ");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta addprefix 0 " + tagprefix);

                        handitem.setAmount(0);
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "You can only apply one nametag at a time!");
                    }
                }
            }
        }
    }
}
