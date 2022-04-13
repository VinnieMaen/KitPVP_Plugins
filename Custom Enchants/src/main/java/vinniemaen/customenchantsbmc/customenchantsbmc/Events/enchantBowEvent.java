package vinniemaen.customenchantsbmc.customenchantsbmc.Events;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vinniemaen.customenchantsbmc.customenchantsbmc.CustomEnchantsBMC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class enchantBowEvent implements Listener {

    String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Bucket" + ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA + "]" + " ";

    @EventHandler
    public void onEnchant(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() != null) {
            ItemStack cursoritem = event.getCursor();
            ItemMeta cursormeta = cursoritem.getItemMeta();
            ItemStack currentitem = event.getCurrentItem();
            ItemMeta currentitemmeta = currentitem.getItemMeta();

            if (currentitem.getType() != Material.AIR) {
                if (currentitem.getType().toString().toLowerCase().contains("bow")) {
                    if (cursormeta != null) {
                        if (cursormeta.getDisplayName().equals(ChatColor.AQUA + "Enchanted Book")) {

                            if (currentitem.getAmount() == 1) {

                                if (cursoritem.getAmount() == 1) {

                                    List<String> lore = cursormeta.getLore();

                                    if (lore != null) {

                                        if (lore.contains(ChatColor.GREEN + "Chance of Success:")) {

                                            String enchantment = lore.get(0);
                                            String arr[] = enchantment.split(" ", 2);
                                            String enchantmentsort = arr[0];
                                            String level = arr[1];

                                            if (Enchantment.getByName(enchantmentsort).getItemTarget().equals(EnchantmentTarget.BOW)) {

                                                    String percentage = lore.get(3);
                                                    int number = Integer.parseInt(percentage.replaceAll("[\\D]", ""));
                                                    Random r = new Random();
                                                    int i = r.nextInt(100);
                                                    ItemMeta swordmeta = currentitem.getItemMeta();
                                                    List<String> swordlore = swordmeta.getLore();

                                                    if (swordlore != null) {
                                                        if (swordlore.contains(ChatColor.GOLD + enchantmentsort + " II")) {
                                                            player.sendMessage(prefix + ChatColor.RED + "You already have the maximum level of this enchant!");
                                                            return;
                                                        }
                                                    }

                                                    if (i < number) {


                                                        if (swordlore == null) {
                                                            ArrayList<String> newlore = new ArrayList<>();

                                                            newlore.add(ChatColor.GOLD + enchantmentsort + " " + level);

                                                            swordmeta.setLore(newlore);

                                                            if (currentitem.getEnchantments().size() == 0) {
                                                                swordmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                                                swordmeta.addEnchant(Enchantment.CHANNELING, 1, true);
                                                            }
                                                            swordmeta.addEnchant(Enchantment.getByName(enchantmentsort), 1, true);

                                                            currentitem.setItemMeta(swordmeta);

                                                            cursoritem.setAmount(0);

                                                            player.updateInventory();
                                                        } else if (swordlore.contains(ChatColor.GOLD + enchantmentsort + " II")) {
                                                            player.sendMessage(prefix + ChatColor.RED + "You already have the maximum level of this enchant!");

                                                        } else if (swordlore.contains(ChatColor.GOLD + enchantmentsort + " I")) {
                                                            ArrayList<String> newlore = new ArrayList<>();
                                                            String staticenchantment = null;
                                                            String staticenchantment2 = null;
                                                            String staticenchantment3 = null;

                                                            if (swordlore.size() == 2) {
                                                                if (swordlore.get(0).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(1);

                                                                }

                                                                if (swordlore.get(1).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(0);

                                                                }
                                                                newlore.add(staticenchantment);
                                                            }
                                                            if (swordlore.size() == 3) {
                                                                if (swordlore.get(0).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(1);
                                                                    staticenchantment2 = swordlore.get(2);

                                                                }

                                                                if (swordlore.get(1).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(0);
                                                                    staticenchantment2 = swordlore.get(2);


                                                                }
                                                                if (swordlore.get(2).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(0);
                                                                    staticenchantment = swordlore.get(1);


                                                                }
                                                                newlore.add(staticenchantment);
                                                                newlore.add(staticenchantment2);
                                                            }
                                                            if (swordlore.size() == 4) {
                                                                if (swordlore.get(0).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(1);
                                                                    staticenchantment2 = swordlore.get(2);
                                                                    staticenchantment3 = swordlore.get(3);

                                                                }

                                                                if (swordlore.get(1).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(0);
                                                                    staticenchantment2 = swordlore.get(2);
                                                                    staticenchantment2 = swordlore.get(3);

                                                                }
                                                                if (swordlore.get(2).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(0);
                                                                    staticenchantment = swordlore.get(1);
                                                                    staticenchantment3 = swordlore.get(3);


                                                                }
                                                                if (swordlore.get(3).equals(ChatColor.GOLD + enchantmentsort + " I")) {
                                                                    staticenchantment = swordlore.get(0);
                                                                    staticenchantment = swordlore.get(1);
                                                                    staticenchantment3 = swordlore.get(2);

                                                                }
                                                                newlore.add(staticenchantment);
                                                                newlore.add(staticenchantment2);
                                                                newlore.add(staticenchantment3);
                                                            }
                                                            if (swordlore.size() == 5) {
                                                                player.sendMessage(prefix + ChatColor.RED + "You already have the maximum amount of custom enchants on this item!");
                                                                return;
                                                            }


                                                            newlore.add(ChatColor.GOLD + enchantmentsort + " II");
                                                            swordmeta.addEnchant(Enchantment.getByName(enchantmentsort), 2, true);

                                                            swordmeta.setLore(newlore);

                                                            currentitem.setItemMeta(swordmeta);

                                                            cursoritem.setAmount(0);

                                                            player.updateInventory();
                                                        } else {
                                                            List newlore = swordmeta.getLore();

                                                            newlore.add(ChatColor.GOLD + enchantmentsort + " " + level);

                                                            currentitemmeta.addEnchant(Enchantment.getByName(enchantmentsort), 1, true);

                                                            currentitemmeta.setLore(newlore);

                                                            currentitem.setItemMeta(currentitemmeta);

                                                            cursoritem.setAmount(0);

                                                            player.updateInventory();
                                                        }

                                                    } else {

                                                        player.sendMessage(prefix + ChatColor.RED + "The enchanting has failed!");
                                                        event.setCancelled(true);
                                                        World world = Bukkit.getWorld("world");
                                                        world.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 10, 1);

                                                        cursoritem.setAmount(0);

                                                        player.updateInventory();

                                                    }
                                                }else {
                                                    player.sendMessage(prefix + ChatColor.RED + "You can't apply this enchantment to this item!");
                                                }
                                            }
                                        }
                                    } else {
                                        player.sendMessage(prefix + ChatColor.RED + "You can't apply this enchantment to this item!");
                                    }

                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "You can only apply one enchantment at a time!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "You can only apply an enchantment to one item at a time!");

                            }

                        }
                    }
            }
        }
    }
}
