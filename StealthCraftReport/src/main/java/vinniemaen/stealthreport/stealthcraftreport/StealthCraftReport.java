package vinniemaen.stealthreport.stealthcraftreport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class StealthCraftReport extends JavaPlugin implements Listener {

    ArrayList<Player> reports = new ArrayList<>();
    String prefix = ChatColor.DARK_AQUA +"["+ ChatColor.GOLD+"Report"+ ChatColor.DARK_AQUA+"]"+" ";
    String reportedplayer = null;
    HashMap<UUID,Long> cooldown = new HashMap<UUID,Long>();
    int cooldowntime = 5;
    long secondsleft;


    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "StealthCraftReport Enabled");
        getServer().getPluginManager().registerEvents(this, this);


    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "StealthCraftReport Disable");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        String name = player.getName();


        if (command.getName().equalsIgnoreCase("report")) {

            if (cooldown.containsKey(player.getUniqueId())) {

                secondsleft = ((cooldown.get(player.getUniqueId()) / 1000) + cooldowntime) - (System.currentTimeMillis() / 1000);
            }

            if (secondsleft > 0) {

                    player.sendMessage(ChatColor.RED + "You have to wait " + secondsleft + " seconds before you can file a new report.");

                }

            if (args.length == 0) {

                player.sendMessage(ChatColor.RED + "/report <player>");

            }

            else if (secondsleft <= 0) {

                String target = args[0];
                reportedplayer = target;
                if (Bukkit.getPlayer(target) != null) {
                    String playerTarget = Bukkit.getPlayer(target).getName();

                    Inventory gui = Bukkit.createInventory(player, 45, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lReport" + ChatColor.DARK_AQUA + "]");

                    if (Bukkit.getPlayer(target) == null) {

                        player.sendMessage(ChatColor.RED + "This player is offline!");
                        return true;

                    }

                    if (Bukkit.getPlayer(target).equals(player)) {
                        player.sendMessage(ChatColor.RED + "You can't report yourself!");

                    } else if (target.equals(playerTarget)) {
                        ItemStack item1 = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                        ItemStack item2 = new ItemStack(Material.DIAMOND_SWORD, 1);
                        ItemStack item3 = new ItemStack(Material.EMERALD, 1);
                        ItemStack item4 = new ItemStack(Material.GOLD_INGOT, 1);
                        ItemStack item5 = new ItemStack(Material.BARRIER, 1);
                        ItemStack item6 = new ItemStack(Material.BARRIER, 1);

                        ItemMeta itemmeta1 = item1.getItemMeta();
                        ItemMeta itemmeta2 = item2.getItemMeta();
                        ItemMeta itemmeta3 = item3.getItemMeta();
                        ItemMeta itemmeta4 = item4.getItemMeta();
                        ItemMeta itemmeta5 = item5.getItemMeta();
                        ItemMeta itemmeta6 = item6.getItemMeta();


                        itemmeta1.setDisplayName(ChatColor.RED + "General Hacks");
                        item1.setItemMeta(itemmeta1);
                        itemmeta2.setDisplayName(ChatColor.RED + "Combat Hacks");
                        item2.setItemMeta(itemmeta2);
                        itemmeta3.setDisplayName(ChatColor.RED + "Chat Offence");
                        item3.setItemMeta(itemmeta3);
                        itemmeta4.setDisplayName(ChatColor.RED + "Autoclicking");
                        item4.setItemMeta(itemmeta4);
                        itemmeta5.setDisplayName(ChatColor.GRAY + "Coming soon...");
                        item5.setItemMeta(itemmeta5);
                        itemmeta6.setDisplayName(ChatColor.GRAY + "Coming soon...");
                        item6.setItemMeta(itemmeta6);

                        gui.setItem(10, item1);
                        gui.setItem(13, item2);
                        gui.setItem(16, item3);
                        gui.setItem(28, item4);
                        gui.setItem(31, item5);
                        gui.setItem(34, item6);

                        cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                        player.openInventory(gui);

                    } else {

                        player.sendMessage(ChatColor.RED + "This player is offline!");
                    }
                }
            }else{
                player.sendMessage(ChatColor.RED + "This player is offline!");
            }
        }
        return false;

    }


    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {

        if (event.getCurrentItem() != null) {
            if (event.getView().getTitle().equals(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lReport" + ChatColor.DARK_AQUA + "]")) {
                event.setCancelled(true);
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "General Hacks")) {

                    Player player = (Player) event.getWhoClicked();
                    String name = player.getName();
                    String reason = "General Hacks";
                    event.setCancelled(false);
                    player.closeInventory();

                    player.sendMessage(prefix + ChatColor.AQUA + "Your report has been sent to all staffmembers!");

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        if (p.hasPermission("report.staff")) {

                            p.sendMessage(ChatColor.DARK_AQUA + "===================" + ChatColor.GOLD + " §lReport " + ChatColor.DARK_AQUA + "===================");
                            p.sendMessage("");
                            p.sendMessage(ChatColor.DARK_AQUA + "> " + ChatColor.GOLD + name + ChatColor.AQUA + " has reported " + ChatColor.GOLD + reportedplayer);
                            p.sendMessage(ChatColor.DARK_AQUA + "> " + ChatColor.AQUA + "Reason: " + ChatColor.GOLD + reason);
                            p.sendMessage("");
                            p.sendMessage(ChatColor.DARK_AQUA + "==============================================");

                        }
                    }


                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Combat Hacks")) {

                    Player player = (Player) event.getWhoClicked();
                    String name = player.getName();
                    String reason = "Combat Hacks";
                    event.setCancelled(false);
                    player.closeInventory();

                    player.sendMessage(prefix + ChatColor.AQUA + "Your report has been sent to all staffmembers!");

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        if (p.hasPermission("report.staff")) {

                            p.sendMessage(ChatColor.DARK_AQUA + "===================" + ChatColor.GOLD + " §lReport " + ChatColor.DARK_AQUA + "===================");
                            p.sendMessage("");
                            p.sendMessage(ChatColor.DARK_AQUA + "> " + ChatColor.GOLD + name + ChatColor.AQUA + " has reported " + ChatColor.GOLD + reportedplayer);
                            p.sendMessage(ChatColor.DARK_AQUA + "> " + ChatColor.AQUA + "Reason: " + ChatColor.GOLD + reason);
                            p.sendMessage("");
                            p.sendMessage(ChatColor.DARK_AQUA + "=============================================");

                        }
                    }


                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Chat Offence")) {

                    Player player = (Player) event.getWhoClicked();
                    String name = player.getName();
                    String reason = "Chat Offense";
                    event.setCancelled(false);
                    player.closeInventory();

                    player.sendMessage(prefix + ChatColor.AQUA + "Your report has been sent to all staffmembers!");

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        if (p.hasPermission("report.staff")) {

                            p.sendMessage(ChatColor.DARK_AQUA + "===================" + ChatColor.GOLD + " §lReport " + ChatColor.DARK_AQUA + "===================");
                            p.sendMessage("");
                            p.sendMessage(ChatColor.DARK_AQUA + "> " + ChatColor.GOLD + name + ChatColor.AQUA + " has reported " + ChatColor.GOLD + reportedplayer);
                            p.sendMessage(ChatColor.DARK_AQUA + "> " + ChatColor.AQUA + "Reason: " + ChatColor.GOLD + reason);
                            p.sendMessage("");
                            p.sendMessage(ChatColor.DARK_AQUA + "=============================================");

                        }
                    }


                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Autoclicking")) {

                    Player player = (Player) event.getWhoClicked();
                    String name = player.getName();
                    String reason = "Autoclicking";
                    event.setCancelled(false);
                    player.closeInventory();

                    player.sendMessage(prefix + ChatColor.AQUA + "Your report has been sent to all staffmembers!");

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        if (p.hasPermission("report.staff")) {

                            p.sendMessage(ChatColor.DARK_AQUA + "===================" + ChatColor.GOLD + " §lReport " + ChatColor.DARK_AQUA + "===================");
                            p.sendMessage("");
                            p.sendMessage(ChatColor.DARK_AQUA + "> " + ChatColor.GOLD + name + ChatColor.AQUA + " has reported " + ChatColor.GOLD + reportedplayer);
                            p.sendMessage(ChatColor.DARK_AQUA + "> " + ChatColor.AQUA + "Reason: " + ChatColor.GOLD + reason);
                            p.sendMessage("");
                            p.sendMessage(ChatColor.DARK_AQUA + "=============================================");

                        }
                    }


                }
            }
        }

    }
}
