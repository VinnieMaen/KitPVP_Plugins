package vinniemaen.customenchantsbmc.customenchantsbmc.Events;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class bookApplyEvent implements Listener {

    String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Bucket" + ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA + "]" + " ";

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() != null) {
            World world = player.getWorld();
            ItemStack cursoritem = event.getCursor();
            ItemMeta cursormeta = cursoritem.getItemMeta();
            ItemStack currentitem = event.getCurrentItem();
            ItemMeta currentitemmeta = currentitem.getItemMeta();

            if (cursormeta != null && currentitemmeta != null) {

                if (cursormeta.getDisplayName().equals(ChatColor.GOLD + "Chance Booster")) {
                    if (currentitem.getAmount() == 1) {
                        if (cursoritem.getAmount() == 1) {

                            List<String> lore = currentitemmeta.getLore();
                            if (lore != null) {
                                if (lore.contains(ChatColor.GREEN + "Chance of Success:")) {

                                    String enchantment = lore.get(0);
                                    String percentage = lore.get(3);
                                    if (lore.contains(ChatColor.GRAY + "Common")) {
                                        int number = Integer.parseInt(percentage.replaceAll("[\\D]", ""));
                                        if (number < 96) {
                                            int newnumber = number + 5;

                                            ArrayList<String> newlore = new ArrayList<>();

                                            newlore.add(enchantment);
                                            newlore.add("");
                                            newlore.add(ChatColor.GREEN + "Chance of Success:");
                                            newlore.add(ChatColor.GREEN + "" + newnumber + "%");
                                            newlore.add("");
                                            newlore.add(ChatColor.GRAY + "Common");

                                            currentitemmeta.setLore(newlore);

                                            currentitem.setItemMeta(currentitemmeta);

                                            player.setItemOnCursor(null);
                                            cursoritem.setAmount(0);
                                            player.updateInventory();
                                            world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);

                                        } else {
                                            //enchant chance cant be boosted
                                            player.sendMessage(prefix + ChatColor.RED + "Chance of Success is already 100%!");
                                        }
                                    }
                                    if (lore.contains(ChatColor.LIGHT_PURPLE + "Rare")) {
                                        int number = Integer.parseInt(percentage.replaceAll("[\\D]", ""));
                                        if (number < 96) {
                                            int newnumber = number + 5;

                                            ArrayList<String> newlore = new ArrayList<>();

                                            newlore.add(enchantment);
                                            newlore.add("");
                                            newlore.add(ChatColor.GREEN + "Chance of Success:");
                                            newlore.add(ChatColor.GREEN + "" + newnumber + "%");
                                            newlore.add("");
                                            newlore.add(ChatColor.LIGHT_PURPLE + "Rare");

                                            currentitemmeta.setLore(newlore);

                                            currentitem.setItemMeta(currentitemmeta);

                                            player.setItemOnCursor(null);
                                            cursoritem.setAmount(0);
                                            player.updateInventory();
                                            world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);

                                        } else {
                                            //enchant chance cant be boosted
                                            player.sendMessage(prefix + ChatColor.RED + "Chance of Success is already 100%!");
                                        }

                                    }
                                    if (lore.contains(ChatColor.GOLD + "Legendary")) {
                                        int number = Integer.parseInt(percentage.replaceAll("[\\D]", ""));
                                        if (number < 96) {
                                            int newnumber = number + 5;

                                            ArrayList<String> newlore = new ArrayList<>();

                                            newlore.add(enchantment);
                                            newlore.add("");
                                            newlore.add(ChatColor.GREEN + "Chance of Success:");
                                            newlore.add(ChatColor.GREEN + "" + newnumber + "%");
                                            newlore.add("");
                                            newlore.add(ChatColor.GOLD + "Legendary");

                                            currentitemmeta.setLore(newlore);

                                            currentitem.setItemMeta(currentitemmeta);

                                            player.setItemOnCursor(null);
                                            cursoritem.setAmount(0);
                                            player.updateInventory();
                                            world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);

                                        } else {
                                            //enchant chance cant be boosted
                                            player.sendMessage(prefix + ChatColor.RED + "Chance of Success is already 100%!");
                                        }

                                    }
                                    if (lore.contains(ChatColor.YELLOW + "Epic")) {
                                        int number = Integer.parseInt(percentage.replaceAll("[\\D]", ""));
                                        if (number < 96) {
                                            int newnumber = number + 5;

                                            ArrayList<String> newlore = new ArrayList<>();

                                            newlore.add(enchantment);
                                            newlore.add("");
                                            newlore.add(ChatColor.GREEN + "Chance of Success:");
                                            newlore.add(ChatColor.GREEN + "" + newnumber + "%");
                                            newlore.add("");
                                            newlore.add(ChatColor.YELLOW + "Epic");

                                            currentitemmeta.setLore(newlore);

                                            currentitem.setItemMeta(currentitemmeta);

                                            player.setItemOnCursor(null);
                                            cursoritem.setAmount(0);
                                            player.updateInventory();
                                            world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);

                                        } else {
                                            //enchant chance cant be boosted
                                            player.sendMessage(prefix + ChatColor.RED + "Chance of Success is already 100%!");
                                        }
                                    }
                                }
                            }

                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "You can only apply one boost at a time!");
                        }

                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "You can only apply a boost to one book!");

                    }
                }
            }
        }
    }
}
