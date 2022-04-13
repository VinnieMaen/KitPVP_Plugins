package bucketmc.kitpvp.bucketmckitpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import me.clip.placeholderapi.PlaceholderAPI;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Events implements Listener {

    BucketMCKitPVP plugin;

    HashMap<UUID,Long> cooldownpvpkit = new HashMap<UUID,Long>();
    HashMap<UUID,Long> cooldownbucketkit = new HashMap<UUID,Long>();
    HashMap<UUID,Long> cooldownwaterbucketkit = new HashMap<UUID,Long>();
    HashMap<UUID,Long> cooldownlavabucketkit = new HashMap<UUID,Long>();
    HashMap<UUID,Long> cooldownmilkbucketkit = new HashMap<UUID,Long>();

    private static HashMap<UUID, Integer> kills = new HashMap<UUID, Integer>();
    private static HashMap<UUID, Integer> deaths = new HashMap<UUID, Integer>();
    private static HashMap<UUID, Double> KD = new HashMap<UUID, Double>();

    private static HashMap<UUID, ItemStack[]> inventory1 = new HashMap<UUID, ItemStack[]>();
    private static HashMap<UUID, ItemStack[]> inventory2 = new HashMap<UUID, ItemStack[]>();
    private static HashMap<UUID, ItemStack[]> inventory3 = new HashMap<UUID, ItemStack[]>();
    private static HashMap<UUID, ItemStack[]> inventory4 = new HashMap<UUID, ItemStack[]>();
    private static HashMap<UUID, ItemStack[]> inventory5 = new HashMap<UUID, ItemStack[]>();

    HashMap<UUID, Scoreboard> scoreboards = new HashMap<>();
    Objective objective;

    DecimalFormat numberFormat = new DecimalFormat("0.0");

    int killcount;
    int deathcount;
    double KDratio;
    double killdeath;
    int onlineplayers;

    int cooldowntimepvp = 20;
    int cooldowntimebucket = 5*60;
    int cooldowntimewaterbucket = 5*60;
    int cooldowntimelavabucket = 10*60;
    int cooldowntimemilkbucket = 10*60;

    long secondsleftpvp;
    long secondsleftbucket;
    long secondsleftwaterbucket;
    long secondsleftlavabucket;
    long secondsleftmilkbucket;

    String prefix = ChatColor.DARK_AQUA +"["+ ChatColor.AQUA+"Bucket"+ ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA+"]"+" ";

    public Events(BucketMCKitPVP instance) {
        plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        UUID uuid = event.getPlayer().getUniqueId();
        Player player = event.getPlayer();

        if (player.hasPermission("bucketmc.staff")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("bucketmc.staff")) {
                    p.sendMessage(prefix + ChatColor.GOLD + player.getName() + ChatColor.AQUA + " Has joined the server!");
                }
            }
        }

        if (Bukkit.getOnlinePlayers().size() != 0) {
            onlineplayers = Bukkit.getOnlinePlayers().size();
        } else {
            onlineplayers = 0;
        }

        if (plugin.getConfig().getString("Data." + uuid) != null) {
            killcount = plugin.getConfig().getInt("Data." + uuid + ".kills");
            kills.put(uuid, killcount);
        } else {
            kills.put(uuid, 0);
        }

        if (plugin.getConfig().getString("Data." + uuid) != null) {
            deathcount = plugin.getConfig().getInt("Data." + uuid + ".deaths");
            deaths.put(uuid, deathcount);
        } else {
            deaths.put(uuid, 0);
        }

        if (plugin.getConfig().getString("Data." + uuid) != null) {
            KDratio = plugin.getConfig().getDouble("Data." + uuid + ".killdeath");
            KD.put(uuid, KDratio);
        } else {
            KD.put(uuid, 0.0);
        }
        plugin.getConfig().set("Data." + uuid + ".kills", kills.get(uuid));
        plugin.getConfig().set("Data." + uuid + ".deaths", deaths.get(uuid));
        plugin.getConfig().set("Data." + uuid + ".killdeath", KD.get(uuid));
        plugin.saveConfig();

        createscoreboard(player);

        if(plugin.inventories.get("Data." + uuid) != null){
            ItemStack[] content1 = ((List<ItemStack>) plugin.inventories.get("Data." + uuid + ".inventory1")).toArray(new ItemStack[0]);
            inventory1.put(uuid, content1);
            ItemStack[] content2 = ((List<ItemStack>) plugin.inventories.get("Data." + uuid + ".inventory2")).toArray(new ItemStack[0]);
            inventory2.put(uuid, content2);
            ItemStack[] content3 = ((List<ItemStack>) plugin.inventories.get("Data." + uuid + ".inventory3")).toArray(new ItemStack[0]);
            inventory3.put(uuid, content3);
            ItemStack[] content4 = ((List<ItemStack>) plugin.inventories.get("Data." + uuid + ".inventory4")).toArray(new ItemStack[0]);
            inventory4.put(uuid, content4);
            ItemStack[] content5 = ((List<ItemStack>) plugin.inventories.get("Data." + uuid + ".inventory5")).toArray(new ItemStack[0]);
            inventory5.put(uuid, content5);

        }else {
            Inventory gui = Bukkit.createInventory(player, 45, "dummy");
            plugin.inventories.set("Data." + uuid + ".inventory1", gui.getContents());
            plugin.inventories.set("Data." + uuid + ".inventory2", gui.getContents());
            plugin.inventories.set("Data." + uuid + ".inventory3", gui.getContents());
            plugin.inventories.set("Data." + uuid + ".inventory4", gui.getContents());
            plugin.inventories.set("Data." + uuid + ".inventory5", gui.getContents());

            try {
                plugin.inventories.save(plugin.f);
            } catch (IOException e) {
                // Handle any IO exception here
                e.printStackTrace();
            }
        }

    }

    public static HashMap<UUID, ItemStack[]> getInventory1(){
        return inventory1;
    }
    public static HashMap<UUID, ItemStack[]> getInventory2(){
        return inventory2;
    }public static HashMap<UUID, ItemStack[]> getInventory3(){
        return inventory3;
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        event.setQuitMessage("");
        UUID uuid = event.getPlayer().getUniqueId();
        onlineplayers = Bukkit.getOnlinePlayers().size();
        Player player = event.getPlayer();

        if (player.hasPermission("bucketmc.staff")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("bucketmc.staff")) {
                    p.sendMessage(prefix + ChatColor.GOLD + player.getName() + ChatColor.AQUA + " Has left the server!");
                }
            }
        }

        plugin.getConfig().set("Data." + uuid + ".kills", kills.get(uuid));
        plugin.getConfig().set("Data." + uuid + ".deaths", deaths.get(uuid));
        plugin.getConfig().set("Data." + uuid + ".killdeath", KD.get(uuid));
        plugin.saveConfig();


        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                Scoreboard playerscore = scoreboards.get(p.getUniqueId());
                onlineplayers = Bukkit.getOnlinePlayers().size();
                if (playerscore != null) {
                    if (playerscore.getTeam("online players") != null) {
                        playerscore.getTeam("online players").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Online: " + ChatColor.GOLD + onlineplayers + "/100");
                    }
                }
            }
    }
        },(2*20));
    }

    public static HashMap<UUID, Integer> getDeaths() {
        return deaths;
    }

    public static HashMap<UUID, Integer> getKills() {
        return kills;
    }

    public static HashMap<UUID, Double> getKD() {
        return KD;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        event.setDeathMessage("");

        double KDratioPlayer;
        double KDratioKiller;
        Player player = event.getEntity();
        UUID uuid = player.getUniqueId();

        if (event.getEntity().getKiller() != null) {

            Player killer = player.getKiller();
            UUID killeruuid = killer.getUniqueId();


            if (kills.get(killeruuid) != null) {
                killcount = kills.get(killeruuid);
            }
            if (deaths.get(uuid) != null) {
                deathcount = deaths.get(uuid);
            }
            if (KD.get(killeruuid) != null) {
                KDratio = KD.get(killeruuid);
            }

            killcount++;
            kills.put(killeruuid, killcount);
            deathcount++;
            deaths.put(uuid, deathcount);
            KDratioPlayer = kills.get(uuid);
            KDratioKiller = kills.get(killeruuid);


            if (deaths.get(uuid) > 0) {
                KDratioPlayer = (double) kills.get(uuid) / deaths.get(uuid);
            } else {
                KDratioPlayer = 1;
            }
            if (deaths.get(killeruuid) > 0) {
                KDratioKiller = (double) kills.get(killeruuid) / deaths.get(killeruuid);
            } else {
                KDratioKiller = 1;
            }

            KD.put(uuid, KDratioPlayer);
            KD.put(killeruuid, KDratioKiller);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    deathcount = 0;
                    killcount = 0;

                }
            }, (2 * 20));
            updateScoreboard(uuid, killeruuid, player, killer);


        }
    }

    private void updateScoreboard(UUID uuid, UUID killeruuid, Player player, Player killer) {

        String balanceplayer = PlaceholderAPI.setPlaceholders(player, "%vault_eco_balance_formatted%");
        String balancekiller = PlaceholderAPI.setPlaceholders(killer, "%vault_eco_balance_formatted%");


        Scoreboard playerscore = scoreboards.get(uuid);
        Scoreboard killerscores = scoreboards.get(killeruuid);

        playerscore.getTeam("kills").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Kills: " + ChatColor.GOLD + kills.get(uuid));
        playerscore.getTeam("deaths").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Deaths: " + ChatColor.GOLD + deaths.get(uuid));
        playerscore.getTeam("K/D").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "K/D: " + ChatColor.GOLD + numberFormat.format(KD.get(uuid)));
        playerscore.getTeam("balance").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Balance: " + ChatColor.GOLD + "€" + balanceplayer);

        killerscores.getTeam("kills").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Kills: " + ChatColor.GOLD + kills.get(killeruuid));
        killerscores.getTeam("deaths").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Deaths: " + ChatColor.GOLD + deaths.get(killeruuid));
        killerscores.getTeam("K/D").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "K/D: " + ChatColor.GOLD + numberFormat.format(KD.get(killeruuid)));
        killerscores.getTeam("balance").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Balance: " + ChatColor.GOLD + "€" + balancekiller);


    }

    public void createscoreboard(Player player) {

        String name = player.getName();
        UUID uuid = player.getUniqueId();
        String balance = PlaceholderAPI.setPlaceholders(player, "%vault_eco_balance_formatted%");

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("scoreboard", "dummy", ChatColor.AQUA + "Bucket" + ChatColor.GOLD + "MC " + ChatColor.RESET.toString() + ChatColor.GRAY + "|" + ChatColor.AQUA + " KitPVP" );

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team killscore = scoreboard.registerNewTeam("kills");
        Team deathscore = scoreboard.registerNewTeam("deaths");
        Team KDscore = scoreboard.registerNewTeam("K/D");
        Team balancescore = scoreboard.registerNewTeam("balance");
        Team onlinecount = scoreboard.registerNewTeam("online players");

        killscore.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
        deathscore.addEntry(ChatColor.WHITE + "" + ChatColor.WHITE);
        KDscore.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        balancescore.addEntry(ChatColor.YELLOW + "" + ChatColor.WHITE);
        onlinecount.addEntry(ChatColor.GRAY + "" + ChatColor.WHITE);


        killscore.setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Kills: " + ChatColor.GOLD + kills.get(uuid));
        deathscore.setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Deaths: " + ChatColor.GOLD + deaths.get(uuid));
        KDscore.setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "K/D: " + ChatColor.GOLD + numberFormat.format(KD.get(uuid)));
        balancescore.setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Balance: " + ChatColor.GOLD + "€" + balance);
        onlinecount.setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Online: " + ChatColor.GOLD + Bukkit.getOnlinePlayers().size() + "/100");


        Score line1 = objective.getScore(ChatColor.DARK_AQUA.toString() + "------------------" );
        Score line2 = objective.getScore(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + player.getName());
        Score end = objective.getScore(ChatColor.RESET + ChatColor.DARK_AQUA.toString() + "------------------" );


        line1.setScore(10);
        line2.setScore(9);
        end.setScore(0);
        objective.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(8);
        objective.getScore(ChatColor.WHITE + "" + ChatColor.WHITE).setScore(7);
        objective.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(6);
        objective.getScore(ChatColor.YELLOW + "" + ChatColor.WHITE).setScore(5);
        objective.getScore(ChatColor.GRAY + "" + ChatColor.WHITE).setScore(4);


        scoreboards.put(uuid, scoreboard);
        loadscoreboard(uuid, player);
    }

    private void loadscoreboard(UUID uuid, Player player) {
        player.setScoreboard(scoreboards.get(uuid));
        onlineplayers = Bukkit.getOnlinePlayers().size();

        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard playerscore = scoreboards.get(p.getUniqueId());
            if (playerscore != null) {
                if (playerscore.getTeam("online players") != null) {
                    playerscore.getTeam("online players").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Online: " + ChatColor.GOLD + onlineplayers + "/100");
                }
            }
        }
    }




    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() != null) {
            if (event.getView().getTitle().equals(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lSelect KIT" + ChatColor.DARK_AQUA + "]")) {
                event.setCancelled(true);

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Kit PVP")) {

                    if (cooldownpvpkit.containsKey(player.getUniqueId())) {

                        secondsleftpvp = ((cooldownpvpkit.get(player.getUniqueId()) / 1000) + cooldowntimepvp) - (System.currentTimeMillis() / 1000);
                    }

                    if (secondsleftpvp > 0) {

                        player.sendMessage(ChatColor.RED + "You have to wait " + ChatColor.DARK_RED + secondsleftpvp + ChatColor.RED + " seconds before you can get a new kit.");
                        player.closeInventory();

                    } else {

                        ItemStack item1 = new ItemStack(Material.IRON_SWORD, 1);
                        ItemStack item2 = new ItemStack(Material.BOW, 1);
                        ItemStack item3 = new ItemStack(Material.ARROW, 64);
                        ItemStack item4 = new ItemStack(Material.GOLDEN_APPLE, 5);
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


                        itemmeta1.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "PVP Sword");

                        itemmeta1.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                        item1.setItemMeta(itemmeta1);

                        itemmeta2.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "PVP Bow");

                        itemmeta2.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                        item2.setItemMeta(itemmeta2);

                        itemmeta5.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "PVP Fishingrod");

                        item5.setItemMeta(itemmeta5);

                        helmetmeta.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "PVP Helmet");

                        helmetmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        helmet.setItemMeta(helmetmeta);


                        chestplatemeta.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "PVP Chestplate");

                        chestplatemeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        chestplate.setItemMeta(chestplatemeta);

                        leggingsmeta.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "PVP Leggings");

                        leggings.setItemMeta(leggingsmeta);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                        bootsmeta.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "PVP Boots");

                        boots.setItemMeta(bootsmeta);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);


                        player.closeInventory();
                        Inventory playerinventory = player.getInventory();


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

                        cooldownpvpkit.put(player.getUniqueId(), System.currentTimeMillis());


                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Kit Bucket")) {
                    if (player.hasPermission("kit.bucket")) {

                        if (cooldownbucketkit.containsKey(player.getUniqueId())) {

                            secondsleftbucket = ((cooldownbucketkit.get(player.getUniqueId()) / 1000) + cooldowntimebucket) - (System.currentTimeMillis() / 1000);
                        }

                        if (secondsleftbucket > 0) {

                            player.sendMessage(ChatColor.RED + "You have to wait " + ChatColor.DARK_RED + secondsleftbucket + ChatColor.RED + " seconds before you can get a new kit.");
                            player.closeInventory();

                        } else {

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


                            player.closeInventory();
                            Inventory playerinventory = player.getInventory();

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

                            cooldownbucketkit.put(player.getUniqueId(), System.currentTimeMillis());
                        }
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Kit WaterBucket")) {
                    if (player.hasPermission("kit.waterbucket")) {

                        if (cooldownwaterbucketkit.containsKey(player.getUniqueId())) {

                            secondsleftwaterbucket = ((cooldownwaterbucketkit.get(player.getUniqueId()) / 1000) + cooldowntimewaterbucket) - (System.currentTimeMillis() / 1000);
                        }

                        if (secondsleftwaterbucket > 0) {

                            player.sendMessage(ChatColor.RED + "You have to wait " + ChatColor.DARK_RED + secondsleftwaterbucket + ChatColor.RED + " seconds before you can get a new kit.");
                            player.closeInventory();

                        } else {

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


                            player.closeInventory();
                            Inventory playerinventory = player.getInventory();

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

                            cooldownwaterbucketkit.put(player.getUniqueId(), System.currentTimeMillis());
                        }
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Kit LavaBucket")) {
                    if (player.hasPermission("kit.lavabucket")) {

                        if (cooldownlavabucketkit.containsKey(player.getUniqueId())) {

                            secondsleftlavabucket = ((cooldownlavabucketkit.get(player.getUniqueId()) / 1000) + cooldowntimelavabucket) - (System.currentTimeMillis() / 1000);
                        }

                        if (secondsleftlavabucket > 0) {

                            player.sendMessage(ChatColor.RED + "You have to wait " + ChatColor.DARK_RED + secondsleftlavabucket + ChatColor.RED + " seconds before you can get a new kit.");
                            player.closeInventory();

                        } else {

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


                            player.closeInventory();
                            Inventory playerinventory = player.getInventory();

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

                            cooldownlavabucketkit.put(player.getUniqueId(), System.currentTimeMillis());
                        }
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Kit MilkBucket")) {
                    if (player.hasPermission("kit.milkbucket")) {

                        if (cooldownmilkbucketkit.containsKey(player.getUniqueId())) {

                            secondsleftmilkbucket = ((cooldownmilkbucketkit.get(player.getUniqueId()) / 1000) + cooldowntimemilkbucket) - (System.currentTimeMillis() / 1000);
                        }

                        if (secondsleftmilkbucket > 0) {

                            player.sendMessage(ChatColor.RED + "You have to wait " + ChatColor.DARK_RED + secondsleftmilkbucket + ChatColor.RED + " seconds before you can get a new kit.");
                            player.closeInventory();

                        } else {

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


                            player.closeInventory();
                            Inventory playerinventory = player.getInventory();

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

                            cooldownmilkbucketkit.put(player.getUniqueId(), System.currentTimeMillis());
                        }
                    }
                }

            }
            if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lSelect BucketVault" + ChatColor.DARK_AQUA + "]")) {
                event.setCancelled(true);
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bucket 1")) {
                    Inventory bucket1 = Bukkit.createInventory(player, 45, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 1" + ChatColor.DARK_AQUA + "]");
                    player.openInventory(bucket1);
                    if (inventory1.get(player.getUniqueId()) != null) {
                        bucket1.setContents(inventory1.get(player.getUniqueId()));
                    }
                }
                if (player.hasPermission("bmc.vault.2")){
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bucket 2")) {
                        Inventory bucket1 = Bukkit.createInventory(player, 45, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 2" + ChatColor.DARK_AQUA + "]");
                        player.openInventory(bucket1);
                        if (inventory2.get(player.getUniqueId()) != null) {
                            bucket1.setContents(inventory2.get(player.getUniqueId()));
                        }
                    }
                }
                if (player.hasPermission("bmc.vault.3")){
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bucket 3")) {
                        Inventory bucket1 = Bukkit.createInventory(player, 45, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 3" + ChatColor.DARK_AQUA + "]");
                        player.openInventory(bucket1);
                        if (inventory3.get(player.getUniqueId()) != null) {
                            bucket1.setContents(inventory3.get(player.getUniqueId()));
                        }
                    }
                }
                if (player.hasPermission("bmc.vault.4")){
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bucket 4")) {
                        Inventory bucket1 = Bukkit.createInventory(player, 45, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 4" + ChatColor.DARK_AQUA + "]");
                        player.openInventory(bucket1);
                        if (inventory4.get(player.getUniqueId()) != null) {
                            bucket1.setContents(inventory4.get(player.getUniqueId()));
                        }
                    }
                }
                if (player.hasPermission("bmc.vault.5")){
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bucket 5")) {
                        Inventory bucket1 = Bukkit.createInventory(player, 45, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 5" + ChatColor.DARK_AQUA + "]");
                        player.openInventory(bucket1);
                        if (inventory5.get(player.getUniqueId()) != null) {
                            bucket1.setContents(inventory5.get(player.getUniqueId()));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 1" + ChatColor.DARK_AQUA + "]")) {
            ItemStack[] contents = event.getInventory().getContents();
            Player player = (Player) event.getPlayer();
            UUID uuid = player.getUniqueId();
            inventory1.put(uuid, contents);
            plugin.inventories.set("Data." + uuid + ".inventory1", inventory1.get(uuid));

            try {
                plugin.inventories.save(plugin.f);
            } catch (IOException e) {
                // Handle any IO exception here
                e.printStackTrace();
            }
        }
        if (event.getView().getTitle().equals(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 2" + ChatColor.DARK_AQUA + "]")) {
            ItemStack[] contents = event.getInventory().getContents();
            Player player = (Player) event.getPlayer();
            UUID uuid = player.getUniqueId();
            inventory2.put(uuid, contents);
            plugin.inventories.set("Data." + uuid + ".inventory2", contents);
            try {
                plugin.inventories.save(plugin.f);
            } catch (IOException e) {
                // Handle any IO exception here
                e.printStackTrace();
            }

        }
        if (event.getView().getTitle().equals(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 3" + ChatColor.DARK_AQUA + "]")) {
            ItemStack[] contents = event.getInventory().getContents();
            Player player = (Player) event.getPlayer();
            UUID uuid = player.getUniqueId();
            inventory3.put(uuid, contents);
            plugin.inventories.set("Data." + uuid + ".inventory3", contents);
            try {
                plugin.inventories.save(plugin.f);
            } catch (IOException e) {
                // Handle any IO exception here
                e.printStackTrace();
            }

        }
        if (event.getView().getTitle().equals(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 4" + ChatColor.DARK_AQUA + "]")) {
            ItemStack[] contents = event.getInventory().getContents();
            Player player = (Player) event.getPlayer();
            UUID uuid = player.getUniqueId();
            inventory4.put(uuid, contents);
            plugin.inventories.set("Data." + uuid + ".inventory4", contents);
            try {
                plugin.inventories.save(plugin.f);
            } catch (IOException e) {
                // Handle any IO exception here
                e.printStackTrace();
            }

        }
        if (event.getView().getTitle().equals(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lBucket 5" + ChatColor.DARK_AQUA + "]")) {
            ItemStack[] contents = event.getInventory().getContents();
            Player player = (Player) event.getPlayer();
            UUID uuid = player.getUniqueId();
            inventory5.put(uuid, contents);
            plugin.inventories.set("Data." + uuid + ".inventory5", contents);
            try {
                plugin.inventories.save(plugin.f);
            } catch (IOException e) {
                // Handle any IO exception here
                e.printStackTrace();
            }

        }
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event){
        event.setMaxPlayers(0);
        event.setMotd("                   §b§lBucket§6§lMC §8| §6§lKitPVP\n     §3» §b§lCustom plugins §8| §b§lCustom Enchants");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String balance = PlaceholderAPI.setPlaceholders(player, "%vault_eco_balance_formatted%");
        UUID playeruuid = player.getUniqueId();
        Scoreboard scoreboard = scoreboards.get(playeruuid);

        scoreboard.getTeam("balance").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Balance: " + ChatColor.GOLD + "€" + balance);

    }

    @EventHandler
    public void onCommand(PlayerCommandSendEvent event){
        Player player = event.getPlayer();
        String balance = PlaceholderAPI.setPlaceholders(player, "%vault_eco_balance_formatted%");
        UUID playeruuid = player.getUniqueId();
        Scoreboard scoreboard = scoreboards.get(playeruuid);

        scoreboard.getTeam("balance").setPrefix(ChatColor.DARK_AQUA + "    > " + ChatColor.AQUA + "Balance: " + ChatColor.GOLD + "€" + balance);

    }
}
