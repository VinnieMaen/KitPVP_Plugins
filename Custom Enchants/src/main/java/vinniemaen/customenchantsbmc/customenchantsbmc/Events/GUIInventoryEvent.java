package vinniemaen.customenchantsbmc.customenchantsbmc.Events;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vinniemaen.customenchantsbmc.customenchantsbmc.CustomEnchantsBMC;

import java.util.ArrayList;
import java.util.Random;

public class GUIInventoryEvent implements Listener {

    String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Bucket" + ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA + "]" + " ";
    double balance;

    public boolean getChance(int minimalChance) {
        Random random = new Random();
        return random.nextInt(99) + 1 >= minimalChance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lAvailable Custom Enchants" + ChatColor.DARK_AQUA + "]") || event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lCustom Enchant Shop" + ChatColor.DARK_AQUA + "]")){
            event.setCancelled(true);
        }
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lCustom Enchants" + ChatColor.DARK_AQUA + "]")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Custom Enchants Shop")) {
                Player player = (Player) event.getWhoClicked();
                Inventory gui = Bukkit.createInventory(player, 27, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lCustom Enchant Shop" + ChatColor.DARK_AQUA + "]");

                ItemStack rare = new ItemStack(Material.BOOK, 1);
                ItemMeta raremeta = rare.getItemMeta();

                ArrayList<String> rarelore = new ArrayList<>();

                rarelore.add(ChatColor.GREEN + "Buy a random enchantment book of type:");
                rarelore.add(ChatColor.LIGHT_PURPLE + "Rare");
                rarelore.add("");
                rarelore.add(ChatColor.GOLD + "Price:");
                rarelore.add(ChatColor.GREEN + "€2000");


                raremeta.setDisplayName(ChatColor.AQUA + "Enchanted Book");
                raremeta.setLore(rarelore);
                raremeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                raremeta.addEnchant(Enchantment.CHANNELING, 1, true);

                rare.setItemMeta(raremeta);

                gui.setItem(12, rare);

                ItemStack common = new ItemStack(Material.BOOK, 1);
                ItemMeta commonmeta = common.getItemMeta();

                ArrayList<String> commonlore = new ArrayList<>();

                commonlore.add(ChatColor.GREEN + "Buy a random enchantment book of type:");
                commonlore.add(ChatColor.GRAY + "Common");
                commonlore.add("");
                commonlore.add(ChatColor.GOLD + "Price:");
                commonlore.add(ChatColor.GREEN + "€1000");


                commonmeta.setDisplayName(ChatColor.AQUA + "Enchanted Book");
                commonmeta.setLore(commonlore);
                commonmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                commonmeta.addEnchant(Enchantment.CHANNELING, 1, true);

                common.setItemMeta(commonmeta);

                gui.setItem(10, common);

                ItemStack epic = new ItemStack(Material.BOOK, 1);
                ItemMeta epicmeta = epic.getItemMeta();

                ArrayList<String> epiclore = new ArrayList<>();

                epiclore.add(ChatColor.GREEN + "Buy a random enchantment book of type:");
                epiclore.add(ChatColor.YELLOW + "Epic");
                epiclore.add("");
                epiclore.add(ChatColor.GOLD + "Price:");
                epiclore.add(ChatColor.GREEN + "€3000");


                epicmeta.setDisplayName(ChatColor.AQUA + "Enchanted Book");
                epicmeta.setLore(epiclore);
                epicmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                epicmeta.addEnchant(Enchantment.CHANNELING, 1, true);

                epic.setItemMeta(epicmeta);

                gui.setItem(14, epic);

                ItemStack legendary = new ItemStack(Material.BOOK, 1);
                ItemMeta legendarymeta = legendary.getItemMeta();

                ArrayList<String> legendarylore = new ArrayList<>();

                legendarylore.add(ChatColor.GREEN + "Buy a random enchantment book of type:");
                legendarylore.add(ChatColor.GOLD + "Legendary");
                legendarylore.add("");
                legendarylore.add(ChatColor.GOLD + "Price:");
                legendarylore.add(ChatColor.GREEN + "€5000");


                legendarymeta.setDisplayName(ChatColor.AQUA + "Enchanted Book");
                legendarymeta.setLore(legendarylore);
                legendarymeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                legendarymeta.addEnchant(Enchantment.CHANNELING, 1, true);

                legendary.setItemMeta(legendarymeta);

                gui.setItem(16, legendary);

                ItemStack blank = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
                ItemMeta blankmeta = blank.getItemMeta();
                blankmeta.setDisplayName(" ");
                blank.setItemMeta(blankmeta);

                gui.setItem(0, blank);
                gui.setItem(1, blank);
                gui.setItem(2, blank);
                gui.setItem(3, blank);
                gui.setItem(4, blank);
                gui.setItem(5, blank);
                gui.setItem(6, blank);
                gui.setItem(7, blank);
                gui.setItem(8, blank);
                gui.setItem(9, blank);
                gui.setItem(11, blank);
                gui.setItem(13, blank);
                gui.setItem(15, blank);
                gui.setItem(17, blank);
                gui.setItem(18, blank);
                gui.setItem(19, blank);
                gui.setItem(20, blank);
                gui.setItem(21, blank);
                gui.setItem(22, blank);
                gui.setItem(23, blank);
                gui.setItem(24, blank);
                gui.setItem(25, blank);
                gui.setItem(26, blank);

                player.openInventory(gui);


            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Available Custom Enchants")) {

                Player player = (Player) event.getWhoClicked();
                Inventory gui = Bukkit.createInventory(player, 54, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lAvailable Custom Enchants" + ChatColor.DARK_AQUA + "]");

                ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
                ItemMeta helmetmeta = helmet.getItemMeta();

                ArrayList<String> helmetlore = new ArrayList<>();

                helmetlore.add(ChatColor.GREEN + "See all the available enchantments for a helmet!");


                helmetmeta.setDisplayName(ChatColor.AQUA + "Helmet Enchantments");
                helmetmeta.setLore(helmetlore);
                helmetmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                helmetmeta.addEnchant(Enchantment.CHANNELING, 1, true);

                helmet.setItemMeta(helmetmeta);

                gui.setItem(13, helmet);

                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
                ItemMeta swordmeta = sword.getItemMeta();

                ArrayList<String> swordlore = new ArrayList<>();

                swordlore.add(ChatColor.GREEN + "See all the available enchantments for a sword!");


                swordmeta.setDisplayName(ChatColor.AQUA + "Sword Enchantments");
                swordmeta.setLore(swordlore);
                swordmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                swordmeta.addEnchant(Enchantment.CHANNELING, 1, true);

                sword.setItemMeta(swordmeta);

                gui.setItem(11, sword);

                ItemStack bow = new ItemStack(Material.BOW, 1);
                ItemMeta bowmeta = bow.getItemMeta();

                ArrayList<String> bowlore = new ArrayList<>();

                bowlore.add(ChatColor.GREEN + "See all the available enchantments for a bow!");


                bowmeta.setDisplayName(ChatColor.AQUA + "Bow Enchantments");
                bowmeta.setLore(bowlore);
                bowmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                bowmeta.addEnchant(Enchantment.CHANNELING, 1, true);

                bow.setItemMeta(bowmeta);

                gui.setItem(15, bow);

                ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                ItemMeta chestplatemeta = chestplate.getItemMeta();

                ArrayList<String> chestplatelore = new ArrayList<>();

                chestplatelore.add(ChatColor.GREEN + "See all the available enchantments for a chestplate!");


                chestplatemeta.setDisplayName(ChatColor.AQUA + "Chestplate Enchantments");
                chestplatemeta.setLore(chestplatelore);
                chestplatemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                chestplatemeta.addEnchant(Enchantment.CHANNELING, 1, true);

                chestplate.setItemMeta(chestplatemeta);

                gui.setItem(22, chestplate);

                ItemStack pants = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                ItemMeta pantsmeta = pants.getItemMeta();

                ArrayList<String> pantslore = new ArrayList<>();

                pantslore.add(ChatColor.GREEN + "See all the available enchantments for leggings!");


                pantsmeta.setDisplayName(ChatColor.AQUA + "Leggings Enchantments");
                pantsmeta.setLore(pantslore);
                pantsmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                pantsmeta.addEnchant(Enchantment.CHANNELING, 1, true);

                pants.setItemMeta(pantsmeta);

                gui.setItem(31, pants);

                ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
                ItemMeta bootsmeta = boots.getItemMeta();

                ArrayList<String> bootslore = new ArrayList<>();

                bootslore.add(ChatColor.GREEN + "See all the available enchantments for boots!");


                bootsmeta.setDisplayName(ChatColor.AQUA + "Boots Enchantments");
                bootsmeta.setLore(bootslore);
                bootsmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                bootsmeta.addEnchant(Enchantment.CHANNELING, 1, true);

                boots.setItemMeta(bootsmeta);

                gui.setItem(40, boots);


                ItemStack blank = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
                ItemMeta blankmeta = blank.getItemMeta();
                blankmeta.setDisplayName(" ");
                blank.setItemMeta(blankmeta);

                gui.setItem(0, blank);
                gui.setItem(1, blank);
                gui.setItem(2, blank);
                gui.setItem(3, blank);
                gui.setItem(4, blank);
                gui.setItem(5, blank);
                gui.setItem(6, blank);
                gui.setItem(7, blank);
                gui.setItem(8, blank);
                gui.setItem(9, blank);
                gui.setItem(10, blank);
                gui.setItem(12, blank);
                gui.setItem(14, blank);
                gui.setItem(16, blank);
                gui.setItem(17, blank);
                gui.setItem(18, blank);
                gui.setItem(19, blank);
                gui.setItem(20, blank);
                gui.setItem(21, blank);
                gui.setItem(23, blank);
                gui.setItem(24, blank);
                gui.setItem(25, blank);
                gui.setItem(26, blank);
                gui.setItem(27, blank);
                gui.setItem(28, blank);
                gui.setItem(29, blank);
                gui.setItem(30, blank);
                gui.setItem(32, blank);
                gui.setItem(33, blank);
                gui.setItem(34, blank);
                gui.setItem(35, blank);
                gui.setItem(36, blank);
                gui.setItem(37, blank);
                gui.setItem(38, blank);
                gui.setItem(39, blank);
                gui.setItem(41, blank);
                gui.setItem(42, blank);
                gui.setItem(43, blank);
                gui.setItem(44, blank);
                gui.setItem(45, blank);
                gui.setItem(46, blank);
                gui.setItem(47, blank);
                gui.setItem(48, blank);
                gui.setItem(49, blank);
                gui.setItem(50, blank);
                gui.setItem(51, blank);
                gui.setItem(52, blank);
                gui.setItem(53, blank);


                player.openInventory(gui);

            }

            }if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lCustom Enchant Shop" + ChatColor.DARK_AQUA + "]")) {
            Player player = (Player) event.getWhoClicked();
            Economy economy = CustomEnchantsBMC.getEconomy();
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                if (event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.GRAY + "Common")) {
                    if (economy.getBalance(player) >= 500000){
                    if (getChance(10)) {

                    }
                    if (getChance(50)) {

                    }
                }else{
                        player.sendMessage(prefix + ChatColor.RED + "You have insufficient funds!");

                    }
                }
                if (event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.LIGHT_PURPLE + "Rare")) {
                    if (economy.getBalance(player) >= 200){
                        if (getChance(10)) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco take " + player.getName() + " 2000");

                        }
                        if (getChance(50)) {

                        }
                    }else{
                        player.sendMessage(prefix + ChatColor.RED + "You have insufficient funds!");

                    }
                }
                if (event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.YELLOW + "Epic")) {
                    if (economy.getBalance(player) >= 200){
                        if (getChance(80)) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco take " + player.getName() + " 3000");

                        }
                        if (getChance(80)) {

                        }
                    }else{
                        player.sendMessage(prefix + ChatColor.RED + "You have insufficient funds!");

                    }
                }
                if (event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.GOLD + "Legendary")) {
                    if (economy.getBalance(player) >= 200){
                        if (getChance(10)) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco take " + player.getName() + " 5000");

                        }
                        if (getChance(50)) {

                        }
                    }else{
                        player.sendMessage(prefix + ChatColor.RED + "You have insufficient funds!");

                    }
                }
            }
        }
    }
}
