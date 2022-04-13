package vinniemaen.bucketrandomdrop.bmclootdrop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Events implements Listener {

    String prefix = ChatColor.DARK_AQUA +"["+ ChatColor.AQUA+"Bucket"+ ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA+"]"+" ";

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null) {
            if (item.getItemMeta() != null) {
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GREEN + "Money Cheque")) {
                    if (item.getAmount() == 1) {
                        List<String> lore = item.getItemMeta().getLore();

                        String percentage = lore.get(1);
                        int number = Integer.parseInt(percentage.replaceAll("[\\D]", ""));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " " + number);

                        item.setAmount(0);
                    }else{
                        player.sendMessage(prefix + ChatColor.RED + "You can only claim one cheque at a time!");
                    }

                }
            }
        }
    }
}
