package vinniemaen.customenchantsbmc.customenchantsbmc;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import vinniemaen.customenchantsbmc.customenchantsbmc.Enchantments.*;
import vinniemaen.customenchantsbmc.customenchantsbmc.Events.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public final class CustomEnchantsBMC extends JavaPlugin implements Listener {

    private static CustomEnchantsBMC plugin;
    public static Explosion explosion;
    public static Lightning lightning;
    public static EuroKill euroKill;
    public static SwordDrop eswitch;
    public static BlindBow blindBow;
    public static BlindSword blindSword;
    public static DamageReturn damageReturn;
    public static TpHere tpHere;
    public static Runaway runaway;
    public static WitherBow witherBow;
    public static WitherSword witherSword;
    public static PoisonousBow poisonousBow;
    public static PoisonousSword poisonousSword;
    public static WeakBow weakBow;
    public static WeakSword weakSword;
    public  static HealHit healHit;

    String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Bucket" + ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA + "]" + " ";
    private static Economy econ = null;
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "BMCCustomENchants Enabled");

        plugin = this;
        explosion = new Explosion("Explosion");
        lightning = new Lightning("Lightning");
        euroKill = new EuroKill("Eurokill");
        eswitch = new SwordDrop("SwordDrop");
        blindBow = new BlindBow("BlindBow");
        blindSword = new BlindSword("BlindSword");
        damageReturn = new DamageReturn("DamageReturn");
        tpHere = new TpHere("TpHere");
        runaway = new Runaway("Runaway");
        witherBow = new WitherBow("WitherBow");
        witherSword = new WitherSword("WitherSword");
        poisonousBow = new PoisonousBow("PoisonousBow");
        poisonousSword = new PoisonousSword("PoisonousSword");
        weakBow = new WeakBow("WeakBow");
        weakSword = new WeakSword("WeakSword");
        healHit = new HealHit("HealHit");

        loadEnchantments(explosion);
        loadEnchantments(lightning);
        loadEnchantments(euroKill);
        loadEnchantments(eswitch);
        loadEnchantments(blindBow);
        loadEnchantments(blindSword);
        loadEnchantments(damageReturn);
        loadEnchantments(tpHere);
        loadEnchantments(runaway);
        loadEnchantments(witherBow);
        loadEnchantments(witherSword);
        loadEnchantments(poisonousBow);
        loadEnchantments(poisonousSword);
        loadEnchantments(weakBow);
        loadEnchantments(weakSword);
        loadEnchantments(healHit);

        Bukkit.getServer().getPluginManager().registerEvents(explosion, this);
        Bukkit.getServer().getPluginManager().registerEvents(lightning, this);
        Bukkit.getServer().getPluginManager().registerEvents(euroKill, this);
        Bukkit.getServer().getPluginManager().registerEvents(blindBow, this);
        Bukkit.getServer().getPluginManager().registerEvents(blindSword, this);
        Bukkit.getServer().getPluginManager().registerEvents(eswitch, this);
        Bukkit.getServer().getPluginManager().registerEvents(damageReturn, this);
        Bukkit.getServer().getPluginManager().registerEvents(tpHere,this);
        Bukkit.getServer().getPluginManager().registerEvents(runaway,this);
        Bukkit.getServer().getPluginManager().registerEvents(witherBow, this);
        Bukkit.getServer().getPluginManager().registerEvents(witherSword, this);
        Bukkit.getServer().getPluginManager().registerEvents(weakBow, this);
        Bukkit.getServer().getPluginManager().registerEvents(weakSword, this);
        Bukkit.getServer().getPluginManager().registerEvents(poisonousBow, this);
        Bukkit.getServer().getPluginManager().registerEvents(poisonousSword, this);
        Bukkit.getServer().getPluginManager().registerEvents(healHit, this);


        Bukkit.getServer().getPluginManager().registerEvents(new bookApplyEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new enchantChestplateEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new enchantWeaponEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new enchantBowEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GUIInventoryEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new enchantBootsEvent(), this);

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "BMCCustomENchants Disabled");
        unloadEnchantments(explosion);
        unloadEnchantments(lightning);
        unloadEnchantments(euroKill);
        unloadEnchantments(eswitch);
        unloadEnchantments(blindSword);
        unloadEnchantments(blindBow);
        unloadEnchantments(damageReturn);
        unloadEnchantments(tpHere);
        unloadEnchantments(runaway);
        unloadEnchantments(witherSword);
        unloadEnchantments(witherBow);
        unloadEnchantments(poisonousSword);
        unloadEnchantments(poisonousBow);
        unloadEnchantments(weakSword);
        unloadEnchantments(weakBow);
        unloadEnchantments(healHit);
    }

    public static CustomEnchantsBMC getPlugin(){
        return plugin;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }


    private void loadEnchantments(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if(registered){
            // It's been registered!
        }
    }

    private void unloadEnchantments(Enchantment enchantment){
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            if(byKey.containsKey(enchantment.getKey())) {
                byKey.remove(enchantment.getKey());

            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            if(byName.containsKey(enchantment.getName())) {
                byName.remove(enchantment.getName());
            }
        } catch (Exception ignored) { }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("enchants") || command.getName().equalsIgnoreCase("enchantments")){
            Player player = (Player) sender;
            Inventory gui = Bukkit.createInventory(player, 27, ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "§lCustom Enchants" + ChatColor.DARK_AQUA + "]");

            ItemStack rare = new ItemStack(Material.BOOK, 1);
            ItemMeta raremeta = rare.getItemMeta();

            ArrayList<String> rarelore = new ArrayList<>();

            rarelore.add(ChatColor.GREEN + "Buy custom enchantment books.");

            raremeta.setDisplayName(ChatColor.AQUA + "Custom Enchants Shop");
            raremeta.setLore(rarelore);
            raremeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            raremeta.addEnchant(Enchantment.CHANNELING,1, true);

            rare.setItemMeta(raremeta);

            gui.setItem(12, rare);

            ItemStack epic = new ItemStack(Material.BOOK, 1);
            ItemMeta epicmeta = epic.getItemMeta();

            ArrayList<String> epiclore = new ArrayList<>();

            epiclore.add(ChatColor.GREEN + "See all the available custom enchantments.");

            epicmeta.setDisplayName(ChatColor.AQUA + "Available Custom Enchants");
            epicmeta.setLore(epiclore);
            epicmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            epicmeta.addEnchant(Enchantment.CHANNELING,1, true);

            epic.setItemMeta(epicmeta);

            gui.setItem(14, epic);

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
            gui.setItem(11, blank);
            gui.setItem(13, blank);
            gui.setItem(15, blank);
            gui.setItem(16, blank);
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

        if (command.getName().equalsIgnoreCase("bmc")) {
            Player player = (Player) sender;
            if(player.hasPermission("bmc.give")){
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("give")) {
                        if (args.length > 1) {
                            String target = args[1];
                            Player targetplayer = Bukkit.getPlayer(target);

                            if (targetplayer != null) {
                                ItemStack paper = new ItemStack(Material.PAPER, 1);

                                ItemMeta papermeta = paper.getItemMeta();

                                ArrayList<String> lore = new ArrayList<>();

                                lore.add(ChatColor.AQUA + "Boosts Chance of Success by 5%");

                                papermeta.setDisplayName(ChatColor.GOLD + "Chance Booster");
                                papermeta.setLore(lore);

                                paper.setItemMeta(papermeta);

                                targetplayer.getInventory().addItem(paper);

                            }else{
                                player.sendMessage(prefix + ChatColor.RED + "That player is not online!");

                            }
                        }else{
                            player.sendMessage(prefix + ChatColor.RED + "Wrong usage! /bmc give <Player>");

                        }

                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "Wrong usage! /bmc give <Player>");

                    }
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Wrong usage! /bmc give <Player>");

                }
            }else{
                sender.sendMessage(prefix + ChatColor.RED + "You don't have permission to use this command!");

            }
        }

        if (command.getName().equalsIgnoreCase("Ench")){
            Player player = (Player) sender;
            Map<Enchantment, Integer> enchantments = player.getInventory().getItemInMainHand().getEnchantments();
            player.sendMessage(String.valueOf(enchantments));
        }

        if (command.getName().equalsIgnoreCase("enchgive")) {
            if (sender.hasPermission("ench.give")) {
                String rarity = args[0].replace('&', '§');
                String ench = args[1];
                Player player = (Player) sender;
                ItemStack book = new ItemStack(Material.BOOK, 1);

                Random random = new Random();
                int chance = 5 * random.nextInt(21);

                ItemMeta bookmeta = book.getItemMeta();

                ArrayList<String> lore = new ArrayList<>();

                lore.add(ChatColor.GOLD + ench + " I");
                lore.add("");
                lore.add(ChatColor.GREEN + "Chance of Success:");
                lore.add(ChatColor.GREEN + "" + chance + "%");
                lore.add("");
                lore.add(rarity);

                bookmeta.setDisplayName(ChatColor.AQUA + "Enchanted Book");
                bookmeta.setLore(lore);
                bookmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                bookmeta.addEnchant(Enchantment.CHANNELING, 1, true);

                book.setItemMeta(bookmeta);

                player.getInventory().addItem(book);
            }else{
                sender.sendMessage(prefix + ChatColor.RED + "You don't have permission to use this command!");

            }

        }

        return false;
    }

}

