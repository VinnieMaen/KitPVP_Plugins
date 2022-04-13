package bucketmc.kitpvp.bucketmckitpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.clip.placeholderapi.PlaceholderAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class BucketMCKitPVP extends JavaPlugin {

    YamlConfiguration inventories;
    File f;
    String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Bucket" + ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA + "]" + " ";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "BucketMC KitPVP Enabled");
        getServer().getPluginManager().registerEvents(new Events(BucketMCKitPVP.this), this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        f = new File(this.getDataFolder(), "inventories.yml");
        inventories = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                // Handle any IO exception here
                e.printStackTrace();
            }
        }


        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Data saved!");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    UUID uuid = p.getUniqueId();

                    if (p.hasPermission("kit.admin")) {
                        p.sendMessage(prefix + ChatColor.RED + "Playerdata saved!");
                    }

                    if (Events.getKills() != null && Events.getDeaths() != null && Events.getKD() != null) {
                        if (Events.getKills().get(uuid) != null) {
                            getConfig().set("Data." + uuid + ".kills", Events.getKills().get(uuid));
                        }
                        if (Events.getKD().get(uuid) != null) {
                            getConfig().set("Data." + uuid + ".deaths", Events.getKD().get(uuid));
                        }
                        if (Events.getDeaths().get(uuid) != null) {
                            getConfig().set("Data." + uuid + ".killdeath", Events.getKD().get(uuid));
                        }
                        saveConfig();
                    }

                    World world = getServer().getWorld("world");//get the world
                    List<Entity> entList = world.getEntities();//get all entities in the world

                    for (Entity current : entList) {//loop through the list
                        if (current instanceof Item) {//make sure we aren't deleting mobs/players
                            current.remove();//remove it
                        }


                    }
                }
            }
        }, 10 * 20, 5 * 60 * 20);


    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "BucketMC KitPVP Disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("kit") || command.getName().equalsIgnoreCase("kits")) {
            if (args.length == 0) {
                Player player = (Player) sender;
                Inventory gui = Bukkit.createInventory(player, 27, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lSelect KIT" + ChatColor.DARK_AQUA + "]");

                ItemStack item1 = new ItemStack(Material.IRON_SWORD, 1);
                ItemStack item2 = new ItemStack(Material.BUCKET, 1);
                ItemStack item3 = new ItemStack(Material.WATER_BUCKET, 1);
                ItemStack item4 = new ItemStack(Material.LAVA_BUCKET, 1);
                ItemStack item5 = new ItemStack(Material.MILK_BUCKET, 1);

                ItemMeta itemmeta1 = item1.getItemMeta();
                ItemMeta itemmeta2 = item2.getItemMeta();
                ItemMeta itemmeta3 = item3.getItemMeta();
                ItemMeta itemmeta4 = item4.getItemMeta();
                ItemMeta itemmeta5 = item5.getItemMeta();

                ArrayList<String> lore1 = new ArrayList<>();
                ArrayList<String> lore2 = new ArrayList<>();
                ArrayList<String> lore3 = new ArrayList<>();
                ArrayList<String> lore4 = new ArrayList<>();
                ArrayList<String> lore5 = new ArrayList<>();

                lore1.add(ChatColor.AQUA + "Click to get kit PVP.");
                lore2.add(ChatColor.AQUA + "Click to get kit Bucket.");
                lore3.add(ChatColor.AQUA + "Click to get kit WaterBucket.");
                lore4.add(ChatColor.AQUA + "Click to get kit LavaBucket.");
                lore5.add(ChatColor.AQUA + "Click to get kit MilkBucket.");

                if (!player.hasPermission("kit.bucket")) {
                    lore2.add(ChatColor.RED + "You don't have the correct rank to get this kit!");
                    lore2.add(ChatColor.RED + "Get the rank by doing /buy");

                }
                if (!player.hasPermission("kit.waterbucket")) {
                    lore3.add(ChatColor.RED + "You don't have the correct rank to get this kit!");
                    lore3.add(ChatColor.RED + "Get the rank by doing /buy");

                }
                if (!player.hasPermission("kit.lavabucket")) {
                    lore4.add(ChatColor.RED + "You don't have the correct rank to get this kit!");
                    lore4.add(ChatColor.RED + "Get the rank by doing /buy");

                }
                if (!player.hasPermission("kit.milkbucket")) {
                    lore5.add(ChatColor.RED + "You don't have the correct rank to get this kit!");
                    lore5.add(ChatColor.RED + "Get the rank by doing /buy");

                }

                itemmeta1.setLore(lore1);
                itemmeta2.setLore(lore2);
                itemmeta3.setLore(lore3);
                itemmeta4.setLore(lore4);
                itemmeta5.setLore(lore5);

                itemmeta1.setDisplayName(ChatColor.GOLD + "Kit PVP");
                item1.setItemMeta(itemmeta1);
                itemmeta2.setDisplayName(ChatColor.GOLD + "Kit Bucket");
                item2.setItemMeta(itemmeta2);
                itemmeta3.setDisplayName(ChatColor.GOLD + "Kit WaterBucket");
                item3.setItemMeta(itemmeta3);
                itemmeta4.setDisplayName(ChatColor.GOLD + "Kit LavaBucket");
                item4.setItemMeta(itemmeta4);
                itemmeta5.setDisplayName(ChatColor.GOLD + "Kit MilkBucket");
                item5.setItemMeta(itemmeta5);

                gui.setItem(9, item1);
                gui.setItem(11, item2);
                gui.setItem(13, item3);
                gui.setItem(15, item4);
                gui.setItem(17, item5);

                player.openInventory(gui);

            } else {
                if (sender.hasPermission("kit.give")) {
                    String target = args[0];
                    Player targetplayer = Bukkit.getPlayer(target);
                    String kit = args[1];
                    if (kit.equalsIgnoreCase("bucket")) {
                        ItemStack item1 = new ItemStack(Material.IRON_SWORD, 1);
                        ItemStack item2 = new ItemStack(Material.BOW, 1);
                        ItemStack item3 = new ItemStack(Material.ARROW, 64);
                        ItemStack item4 = new ItemStack(Material.GOLDEN_APPLE, 10);
                        ItemStack item5 = new ItemStack(Material.FISHING_ROD, 1);
                        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 32);
                        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
                        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
                        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
                        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);


                        ItemMeta itemmeta1 = item1.getItemMeta();
                        ItemMeta itemmeta2 = item2.getItemMeta();
                        ItemMeta itemmeta5 = item5.getItemMeta();
                        ItemMeta helmetmeta = helmet.getItemMeta();
                        ItemMeta chestplatemeta = chestplate.getItemMeta();
                        ItemMeta leggingsmeta = leggings.getItemMeta();
                        ItemMeta bootsmeta = boots.getItemMeta();


                        itemmeta1.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Bucket Sword");

                        itemmeta1.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                        itemmeta1.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        item1.setItemMeta(itemmeta1);

                        itemmeta2.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Bucket Bow");

                        itemmeta2.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
                        itemmeta2.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
                        item2.setItemMeta(itemmeta2);

                        itemmeta5.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Bucket Fishingrod");

                        item5.setItemMeta(itemmeta5);

                        helmetmeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Bucket Helmet");

                        helmetmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                        helmet.setItemMeta(helmetmeta);


                        chestplatemeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Bucket Chestplate");

                        chestplatemeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                        chestplate.setItemMeta(chestplatemeta);

                        leggingsmeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Bucket Leggings");

                        leggings.setItemMeta(leggingsmeta);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                        bootsmeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "Bucket Boots");

                        boots.setItemMeta(bootsmeta);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);


                        targetplayer.closeInventory();
                        Inventory playerinventory = targetplayer.getInventory();

                        playerinventory.addItem(item1);
                        playerinventory.addItem(item2);
                        playerinventory.addItem(item4);
                        playerinventory.addItem(item3);
                        playerinventory.addItem(item5);
                        playerinventory.addItem(steak);
                        playerinventory.addItem(helmet);
                        playerinventory.addItem(chestplate);
                        playerinventory.addItem(leggings);
                        playerinventory.addItem(boots);

                    }
                    if (kit.equalsIgnoreCase("waterbucket")) {
                        ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD, 1);
                        ItemStack item2 = new ItemStack(Material.BOW, 1);
                        ItemStack item3 = new ItemStack(Material.ARROW, 64);
                        ItemStack item4 = new ItemStack(Material.GOLDEN_APPLE, 15);
                        ItemStack notchapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                        ItemStack item5 = new ItemStack(Material.FISHING_ROD, 1);
                        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 32);
                        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);


                        ItemMeta itemmeta1 = item1.getItemMeta();
                        ItemMeta itemmeta2 = item2.getItemMeta();
                        ItemMeta itemmeta5 = item5.getItemMeta();
                        ItemMeta helmetmeta = helmet.getItemMeta();
                        ItemMeta chestplatemeta = chestplate.getItemMeta();
                        ItemMeta leggingsmeta = leggings.getItemMeta();
                        ItemMeta bootsmeta = boots.getItemMeta();


                        itemmeta1.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "WaterBucket Sword");

                        itemmeta1.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                        itemmeta1.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        item1.setItemMeta(itemmeta1);

                        itemmeta2.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "WaterBucket Bow");

                        itemmeta2.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
                        itemmeta2.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
                        item2.setItemMeta(itemmeta2);

                        itemmeta5.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "WaterBucket Fishingrod");

                        item5.setItemMeta(itemmeta5);

                        helmetmeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "WaterBucket Helmet");

                        helmetmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        helmet.setItemMeta(helmetmeta);


                        chestplatemeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "WaterBucket Chestplate");

                        chestplatemeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        chestplate.setItemMeta(chestplatemeta);

                        leggingsmeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "WaterBucket Leggings");

                        leggings.setItemMeta(leggingsmeta);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                        bootsmeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "WaterBucket Boots");

                        boots.setItemMeta(bootsmeta);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);


                        targetplayer.closeInventory();
                        Inventory playerinventory = targetplayer.getInventory();

                        playerinventory.addItem(item1);
                        playerinventory.addItem(item2);
                        playerinventory.addItem(notchapple);
                        playerinventory.addItem(item4);
                        playerinventory.addItem(item3);
                        playerinventory.addItem(item5);
                        playerinventory.addItem(steak);
                        playerinventory.addItem(helmet);
                        playerinventory.addItem(chestplate);
                        playerinventory.addItem(leggings);
                        playerinventory.addItem(boots);
                    }if (kit.equalsIgnoreCase("lavabucket")) {

                        ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD, 1);
                        ItemStack item2 = new ItemStack(Material.BOW, 1);
                        ItemStack item3 = new ItemStack(Material.ARROW, 64);
                        ItemStack item4 = new ItemStack(Material.GOLDEN_APPLE, 15);
                        ItemStack notchapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                        ItemStack item5 = new ItemStack(Material.FISHING_ROD, 1);
                        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 32);
                        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);


                        ItemMeta itemmeta1 = item1.getItemMeta();
                        ItemMeta itemmeta2 = item2.getItemMeta();
                        ItemMeta itemmeta5 = item5.getItemMeta();
                        ItemMeta helmetmeta = helmet.getItemMeta();
                        ItemMeta chestplatemeta = chestplate.getItemMeta();
                        ItemMeta leggingsmeta = leggings.getItemMeta();
                        ItemMeta bootsmeta = boots.getItemMeta();


                        itemmeta1.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LavaBucket Sword");

                        itemmeta1.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                        itemmeta1.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        itemmeta1.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                        item1.setItemMeta(itemmeta1);

                        itemmeta2.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LavaBucket Bow");

                        itemmeta2.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
                        itemmeta2.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
                        itemmeta2.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                        item2.setItemMeta(itemmeta2);

                        itemmeta5.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LavaBucket Fishingrod");

                        item5.setItemMeta(itemmeta5);

                        helmetmeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LavaBucket Helmet");

                        helmetmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                        helmetmeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);

                        helmet.setItemMeta(helmetmeta);


                        chestplatemeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LavaBucket Chestplate");

                        chestplatemeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                        chestplatemeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
                        chestplate.setItemMeta(chestplatemeta);

                        leggingsmeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LavaBucket Leggings");

                        leggings.setItemMeta(leggingsmeta);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        leggingsmeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);

                        bootsmeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "LavaBucket Boots");

                        boots.setItemMeta(bootsmeta);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        bootsmeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);


                        targetplayer.closeInventory();
                        Inventory playerinventory = targetplayer.getInventory();

                        playerinventory.addItem(item1);
                        playerinventory.addItem(item2);
                        playerinventory.addItem(notchapple);
                        playerinventory.addItem(item4);
                        playerinventory.addItem(item3);
                        playerinventory.addItem(item5);
                        playerinventory.addItem(steak);
                        playerinventory.addItem(helmet);
                        playerinventory.addItem(chestplate);
                        playerinventory.addItem(leggings);
                        playerinventory.addItem(boots);


                    }if (kit.equalsIgnoreCase("milkbucket")) {

                        ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD, 1);
                        ItemStack item2 = new ItemStack(Material.BOW, 1);
                        ItemStack item3 = new ItemStack(Material.ARROW, 64);
                        ItemStack item4 = new ItemStack(Material.GOLDEN_APPLE, 15);
                        ItemStack notchapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                        ItemStack item5 = new ItemStack(Material.FISHING_ROD, 1);
                        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 32);
                        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);


                        ItemMeta itemmeta1 = item1.getItemMeta();
                        ItemMeta itemmeta2 = item2.getItemMeta();
                        ItemMeta itemmeta5 = item5.getItemMeta();
                        ItemMeta helmetmeta = helmet.getItemMeta();
                        ItemMeta chestplatemeta = chestplate.getItemMeta();
                        ItemMeta leggingsmeta = leggings.getItemMeta();
                        ItemMeta bootsmeta = boots.getItemMeta();


                        itemmeta1.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "MilkBucket Sword");

                        itemmeta1.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
                        itemmeta1.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        itemmeta1.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                        item1.setItemMeta(itemmeta1);

                        itemmeta2.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "MilkBucket Bow");

                        itemmeta2.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
                        itemmeta2.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
                        itemmeta2.addEnchant(Enchantment.ARROW_FIRE, 2, true);
                        item2.setItemMeta(itemmeta2);

                        itemmeta5.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "MilkBucket Fishingrod");

                        item5.setItemMeta(itemmeta5);

                        helmetmeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "MilkBucket Helmet");

                        helmetmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                        helmetmeta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
                        helmet.setItemMeta(helmetmeta);


                        chestplatemeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "MilkBucket Chestplate");

                        chestplatemeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                        chestplatemeta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
                        chestplate.setItemMeta(chestplatemeta);

                        leggingsmeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "MilkBucket Leggings");

                        leggings.setItemMeta(leggingsmeta);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        leggings.addEnchantment(Enchantment.PROTECTION_FIRE, 2);

                        bootsmeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "MilkBucket Boots");

                        boots.setItemMeta(bootsmeta);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        boots.addEnchantment(Enchantment.PROTECTION_FIRE, 2);


                        targetplayer.closeInventory();
                        Inventory playerinventory = targetplayer.getInventory();

                        playerinventory.addItem(item1);
                        playerinventory.addItem(item2);
                        playerinventory.addItem(notchapple);
                        playerinventory.addItem(item4);
                        playerinventory.addItem(item3);
                        playerinventory.addItem(item5);
                        playerinventory.addItem(steak);
                        playerinventory.addItem(helmet);
                        playerinventory.addItem(chestplate);
                        playerinventory.addItem(leggings);
                        playerinventory.addItem(boots);

                    }
                }else{
                    sender.sendMessage(prefix + ChatColor.RED + "You don't have permission to use this command!");

                }
            }
        }
        if (command.getName().equalsIgnoreCase("bucket")) {
            Player player = (Player) sender;
            Inventory gui = Bukkit.createInventory(player, 27, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lSelect BucketVault" + ChatColor.DARK_AQUA + "]");

            ItemStack item1 = new ItemStack(Material.COD_BUCKET, 1);
            ItemStack item2 = new ItemStack(Material.PUFFERFISH_BUCKET, 1);
            ItemStack item3 = new ItemStack(Material.TROPICAL_FISH_BUCKET, 1);
            ItemStack item4 = new ItemStack(Material.SALMON_BUCKET, 1);
            ItemStack item5 = new ItemStack(Material.MILK_BUCKET, 1);

            ItemMeta itemmeta1 = item1.getItemMeta();
            ItemMeta itemmeta2 = item2.getItemMeta();
            ItemMeta itemmeta3 = item3.getItemMeta();
            ItemMeta itemmeta4 = item4.getItemMeta();
            ItemMeta itemmeta5 = item5.getItemMeta();

            ArrayList<String> lore1 = new ArrayList<>();
            ArrayList<String> lore2 = new ArrayList<>();
            ArrayList<String> lore3 = new ArrayList<>();
            ArrayList<String> lore4 = new ArrayList<>();
            ArrayList<String> lore5 = new ArrayList<>();

            lore1.add(ChatColor.AQUA + "Click to open BucketVault 1");
            lore2.add(ChatColor.AQUA + "Click to open BucketVault 2");
            lore3.add(ChatColor.AQUA + "Click to open BucketVault 3");
            lore4.add(ChatColor.AQUA + "Click to open BucketVault 4");
            lore5.add(ChatColor.AQUA + "Click to open BucketVault 5");

            if (!player.hasPermission("bmc.vault.2")) {
                lore2.add(ChatColor.RED + "You don't have the correct rank to access this bucketvault");
                lore2.add(ChatColor.RED + "Get the rank by doing /buy");

            }
            if (!player.hasPermission("bmc.vault.3")) {
                lore3.add(ChatColor.RED + "You don't have the correct rank to access this bucketvault");
                lore3.add(ChatColor.RED + "Get the rank by doing /buy");

            }
            if (!player.hasPermission("bmc.vault.4")) {
                lore4.add(ChatColor.RED + "You don't have the correct rank to access this bucketvault");
                lore4.add(ChatColor.RED + "Get the rank by doing /buy");

            }
            if (!player.hasPermission("bmc.vault.5")) {
                lore5.add(ChatColor.RED + "You don't have the correct rank to access this bucketvault");
                lore5.add(ChatColor.RED + "Get the rank by doing /buy");

            }

            itemmeta1.setLore(lore1);
            itemmeta2.setLore(lore2);
            itemmeta3.setLore(lore3);
            itemmeta4.setLore(lore4);
            itemmeta5.setLore(lore5);

            itemmeta1.setDisplayName(ChatColor.GOLD + "Bucket 1");
            item1.setItemMeta(itemmeta1);
            itemmeta2.setDisplayName(ChatColor.GOLD + "Bucket 2");
            item2.setItemMeta(itemmeta2);
            itemmeta3.setDisplayName(ChatColor.GOLD + "Bucket 3");
            item3.setItemMeta(itemmeta3);
            itemmeta4.setDisplayName(ChatColor.GOLD + "Bucket 4");
            item4.setItemMeta(itemmeta4);
            itemmeta5.setDisplayName(ChatColor.GOLD + "Bucket 5");
            item5.setItemMeta(itemmeta5);

            gui.setItem(9, item1);
            gui.setItem(11, item2);
            gui.setItem(13, item3);
            gui.setItem(15, item4);
            gui.setItem(17, item5);

            player.openInventory(gui);

        }
            return false;
        }

}
