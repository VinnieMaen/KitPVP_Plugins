package vinniemaen.bucketrandomdrop.bmclootdrop;

import com.google.common.base.Preconditions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class BMCLootDrop extends JavaPlugin {

    Location min;
    Location max;
    String prefix = ChatColor.DARK_AQUA +"["+ ChatColor.AQUA+"Bucket"+ ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA+"]"+" ";
    DecimalFormat numberFormat = new DecimalFormat("0.0");
    int lootdroptimer;
    boolean lbenabled = false;
    Material originalblock;
    Location randomlocaton;
    double x;
    double y;
    double z;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "BMCLootdrops Enabled");
        lbenabled = false;
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);


    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "BMCLootdrops Disabled");
    }

     public Location getRandomLocation(Location loc1, Location loc2) {
         Preconditions.checkArgument(loc1.getWorld() == loc2.getWorld());
         World world = Bukkit.getWorld("world");
         double minX = Math.min(loc1.getX(), loc2.getX());
         double minZ = Math.min(loc1.getZ(), loc2.getZ());

         double maxX = Math.max(loc1.getX(), loc2.getX());
         double maxZ = Math.max(loc1.getZ(), loc2.getZ());

         int x = (int) randomDouble(minX, maxX);
         int z = (int) randomDouble(minZ, maxZ);

         Block b = world.getHighestBlockAt(x, z);
         Location randomlocationlocation = null;

         if (b.getLocation().getY() < 47) {

             randomlocationlocation = new Location(loc1.getWorld(), randomDouble(minX, maxX), world.getHighestBlockAt(x,z).getY() + 1, randomDouble(minZ, maxZ));
             return randomlocationlocation;
         }else{
             getRandomLocation(min,max);
         }
         return randomlocationlocation;
     }

    public double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("sbenable")) {
            if (sender.hasPermission("bmc.sb")) {
                World world = Bukkit.getWorld("world");
                min = new Location(world, 177, 38, -329);
                max = new Location(world, 2, 4, -116);
                if (lbenabled == false) {
                    randomlocaton = getRandomLocation(min, max);
                    lbenabled = !lbenabled;
                    sender.sendMessage(prefix + ChatColor.AQUA + "You have enabled SupplyBuckets!");

                    lootdroptimer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(BMCLootDrop.this, new Runnable() {
                        @Override
                        public void run() {

                            randomlocaton = getRandomLocation(min, max);

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BMCLootDrop.this, new Runnable() {
                                @Override
                                public void run() {


                            if (randomlocaton != null) {
                                x = randomlocaton.getX();
                                y = randomlocaton.getY();
                                z = randomlocaton.getZ();

                                originalblock = randomlocaton.getBlock().getType();
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(prefix + ChatColor.AQUA + "A LootBucket has been dropped at " + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "\nX: " + numberFormat.format(x) + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + " Y: " + numberFormat.format(y) + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + " Z: " + numberFormat.format(z));
                                    p.sendMessage(prefix + ChatColor.AQUA + "It will despawn in 2 minutes!");
                                }
                                randomlocaton.getBlock().setType(Material.CHEST);
                                Chest chest = (Chest) randomlocaton.getBlock().getState();
                                Inventory inv = chest.getInventory();
                                chest.setCustomName(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lSupplyBucket" + ChatColor.DARK_AQUA + "]");

                                Random rnd = new Random();
                                int i = rnd.nextInt(180);
                                int rslot = rnd.nextInt(27);
                                int rslot2 = rnd.nextInt(27);
                                int rslot3 = rnd.nextInt(27);


                                if (i < 5) {
                                    ItemStack ds = new ItemStack(Material.DIAMOND_SWORD, 1);
                                    ItemStack godapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                                    ds.addEnchantment(Enchantment.DAMAGE_ALL, 3);
                                    ds.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                                    ItemMeta dm = ds.getItemMeta();
                                    dm.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LootBucket UltimateSword");
                                    ds.setItemMeta(dm);
                                    chest.update();

                                    inv.setItem(rslot, ds);
                                    inv.setItem(rslot2, godapple);


                                }
                                if (i > 4 && i < 15) {
                                    ItemStack ds = new ItemStack(Material.DIAMOND_SWORD, 1);
                                    ItemStack godapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                                    ds.addEnchantment(Enchantment.DAMAGE_ALL, 3);
                                    ItemMeta dm = ds.getItemMeta();
                                    dm.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LootBucket Sword");
                                    ds.setItemMeta(dm);
                                    chest.update();

                                    inv.setItem(rslot, ds);
                                    inv.setItem(rslot2, godapple);


                                }
                                if (i > 16 && i < 37) {
                                    ItemStack diamondchest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                                    ItemStack arrows = new ItemStack(Material.ARROW, 32);
                                    diamondchest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                                    ItemMeta dm = diamondchest.getItemMeta();
                                    dm.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LootBucket Chestplate");
                                    diamondchest.setItemMeta(dm);
                                    chest.update();

                                    ItemStack check = new ItemStack(Material.PAPER, 1);

                                    ItemMeta checkmeta = check.getItemMeta();

                                    checkmeta.setDisplayName(ChatColor.DARK_GREEN + "Money Cheque");


                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.GREEN + "Amount:");
                                    lore.add(ChatColor.GREEN + "€50");
                                    lore.add(ChatColor.GREEN + "");
                                    lore.add(ChatColor.GREEN + "Right click to claim!");

                                    checkmeta.setLore(lore);

                                    check.setItemMeta(checkmeta);

                                    inv.setItem(rslot, diamondchest);
                                    inv.setItem(rslot2, arrows);
                                    inv.setItem(rslot3, check);


                                }
                                if (i > 38 && i < 69) {
                                    ItemStack diamondboots = new ItemStack(Material.DIAMOND_BOOTS, 1);
                                    ItemStack check = new ItemStack(Material.PAPER, 1);

                                    diamondboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                                    ItemMeta dm = diamondboots.getItemMeta();
                                    ItemMeta checkmeta = check.getItemMeta();

                                    checkmeta.setDisplayName(ChatColor.DARK_GREEN + "Money Cheque");
                                    dm.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LootBucket Boots");


                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.GREEN + "Amount:");
                                    lore.add(ChatColor.GREEN + "€50");
                                    lore.add(ChatColor.GREEN + "");
                                    lore.add(ChatColor.GREEN + "Right click to claim!");

                                    checkmeta.setLore(lore);

                                    diamondboots.setItemMeta(dm);
                                    check.setItemMeta(checkmeta);
                                    chest.update();

                                    inv.setItem(rslot, diamondboots);
                                    inv.setItem(rslot2, check);


                                }
                                if (i > 70 && i < 111) {
                                    ItemStack diamondhelmet = new ItemStack(Material.DIAMOND_HELMET, 1);
                                    diamondhelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                                    ItemMeta diamondhelmetmeta = diamondhelmet.getItemMeta();
                                    diamondhelmetmeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LootBucketSword");
                                    diamondhelmet.setItemMeta(diamondhelmetmeta);
                                    chest.update();

                                    ItemStack check = new ItemStack(Material.PAPER, 1);

                                    ItemMeta checkmeta = check.getItemMeta();

                                    checkmeta.setDisplayName(ChatColor.DARK_GREEN + "Money Cheque");


                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.GREEN + "Amount:");
                                    lore.add(ChatColor.GREEN + "€20");
                                    lore.add(ChatColor.GREEN + "");
                                    lore.add(ChatColor.GREEN + "Right click to claim!");

                                    checkmeta.setLore(lore);

                                    check.setItemMeta(checkmeta);

                                    inv.setItem(rslot, diamondhelmet);
                                    inv.setItem(rslot2, check);


                                }
                                if (i > 111 && i < 162) {
                                    ItemStack ds = new ItemStack(Material.DIAMOND_SWORD, 1);
                                    ItemStack godapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                                    ds.addEnchantment(Enchantment.DAMAGE_ALL, 3);
                                    ItemMeta dm = ds.getItemMeta();
                                    dm.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LootBucketSword");
                                    ds.setItemMeta(dm);
                                    chest.update();

                                    inv.setItem(rslot, ds);
                                    inv.setItem(rslot2, godapple);


                                } if (i > 161) {
                                    ItemStack arrows = new ItemStack(Material.ARROW, 32);
                                    ItemStack ds = new ItemStack(Material.GOLDEN_APPLE, 5);
                                    chest.update();
                                    inv.setItem(rslot2, arrows);
                                    inv.setItem(rslot, ds);
                                }

                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BMCLootDrop.this, new Runnable() {
                                    @Override
                                    public void run() {
                                        randomlocaton.getBlock().setType(originalblock);
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendMessage(prefix + ChatColor.AQUA + "The " + ChatColor.GOLD + "SupplyBucket " + ChatColor.AQUA + "has despawned!");
                                        }
                                    }
                                }, (2 * 60 * 20));
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BMCLootDrop.this, new Runnable() {
                                    @Override
                                    public void run() {
                                        randomlocaton.getBlock().setType(originalblock);
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendMessage(prefix + ChatColor.AQUA + "A SupplyBucket will be dropped in " + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "10" + ChatColor.AQUA + " seconds!");
                                        }
                                    }
                                }, (30 * 60 * 20 - 10 * 20));
                            }
                                }
                            }, (2 * 20));
                        }
                    }, 30 * 60 * 20, 30 * 60 * 20);


                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BMCLootDrop.this, new Runnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(prefix + ChatColor.AQUA + "The first SupplyBucket will be dropped in 30 minutes!");

                        }
                    }, (5 * 20));

                }else{
                    sender.sendMessage(prefix + ChatColor.RED + "SupplyBuckets is already enabled!");
            }

            }
            }
            if (command.getName().equalsIgnoreCase("sbdisable")) {
                if (sender.hasPermission("bmc.sb")) {
                    if (lbenabled == true) {
                        lbenabled = !lbenabled;
                        sender.sendMessage(prefix + ChatColor.AQUA + "You have disabled supplybuckets!");
                        Bukkit.getScheduler().cancelTask(lootdroptimer);
                    }else{
                        sender.sendMessage(prefix + ChatColor.RED + "SupplyBuckets is already disabled!");
                }
                }
            }
            if (command.getName().equalsIgnoreCase("cheque")) {
                if (sender.hasPermission("cheque.give")) {
                    if (args.length > 0) {
                        String moneyamount = args[0];
                        ItemStack check = new ItemStack(Material.PAPER, 1);

                        ItemMeta checkmeta = check.getItemMeta();

                        checkmeta.setDisplayName(ChatColor.DARK_GREEN + "Money Cheque");


                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GREEN + "Amount:");
                        lore.add(ChatColor.GREEN + "€" + moneyamount);
                        lore.add(ChatColor.GREEN + "");
                        lore.add(ChatColor.GREEN + "Right click to claim!");

                        checkmeta.setLore(lore);

                        check.setItemMeta(checkmeta);

                        Player player = (Player) sender;
                        player.getInventory().addItem(check);
                    }
                }
            }
        return false;
    }
}
