package vinniemaen.antixray.antixray;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class AntiXray extends JavaPlugin implements Listener {

    AtomicInteger processId = new AtomicInteger();
    int amount1;
    int amount2;
    int amount3;
    int amount4;
    int amount;
    String prefix = ChatColor.DARK_AQUA +"["+ChatColor.GOLD+"AntiXray"+ChatColor.DARK_AQUA+"]"+" ";
    HashMap<Player,Integer> amounts1 = new HashMap();
    HashMap<Player,Integer> amounts2 = new HashMap();
    HashMap<Player,Integer> amounts3 = new HashMap();
    HashMap<Player,Integer> amounts4 = new HashMap();

    @Override
    public void onEnable() {
        System.out.println("AntiXray Enabled");
        getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        System.out.println("AntiXray Disabled");
    }

    public void onBlockMineAlert(Player player, String ore, String name, int amount) {

        Bukkit.getScheduler().cancelTask(processId.get());

        if (amounts1.get(player) != 0 | amounts2.get(player) != 0 | amounts3.get(player) != 0 | amounts4.get(player) != 0) {
            int taskID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.hasPermission("antixray.access")) {

                            if (ore.equalsIgnoreCase("Diamond_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.AQUA + " Has mined " + ChatColor.RED + amount + ChatColor.AQUA + " Diamonds!");
                            }
                            if (ore.equalsIgnoreCase("Gold_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.GOLD + " Has mined " + ChatColor.RED + amount + ChatColor.GOLD + " Pieces of gold ore!");
                            }
                            if (ore.equalsIgnoreCase("Emerald_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.GREEN + " Has mined " + ChatColor.RED + amount + ChatColor.GREEN + " Emeralds!");
                            }
                            if (ore.equalsIgnoreCase("Iron_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.GRAY + " Has mined " + ChatColor.RED + amount + ChatColor.GRAY + " Pieces of iron ore!");
                            }
                            if (ore.equalsIgnoreCase("Lapis_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.BLUE + " Has mined " + ChatColor.RED + amount + ChatColor.BLUE + " Pieces of lapis!");
                            }

                        }
                    }
                    amounts1.put(player, 0);
                    amounts2.put(player, 0);
                    amounts3.put(player, 0);
                    amounts4.put(player, 0);


                }
            }, (2 * 20));
            processId.set(taskID);

        }else{

            int taskID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.hasPermission("antixray.access")) {

                            if (ore.equalsIgnoreCase("Diamond_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.AQUA + " Has mined " + ChatColor.RED + amount + ChatColor.AQUA + " Diamonds!");
                            }
                            if (ore.equalsIgnoreCase("Gold_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.GOLD + " Has mined " + ChatColor.RED + amount + ChatColor.GOLD + " Pieces of gold ore!");
                            }
                            if (ore.equalsIgnoreCase("Emerald_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.GREEN + " Has mined " + ChatColor.RED + amount + ChatColor.GREEN + " Emeralds!");
                            }
                            if (ore.equalsIgnoreCase("Iron_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.GRAY + " Has mined " + ChatColor.RED + amount + ChatColor.GRAY + " Pieces of iron ore!");
                            }
                            if (ore.equalsIgnoreCase("Lapis_Ore")) {
                                p.sendMessage(prefix + ChatColor.GOLD + name + ChatColor.BLUE + " Has mined " + ChatColor.RED + amount + ChatColor.BLUE + " Pieces of lapis!");
                            }

                        }
                    }
                    amounts1.put(player, 0);
                    amounts2.put(player, 0);
                    amounts3.put(player, 0);
                    amounts4.put(player, 0);


                }
            }, (2 * 20));

    }

    }

    @EventHandler
    public void onDiamondCollect(BlockBreakEvent event) {

        Player player = (Player) event.getPlayer();
        String name = player.getDisplayName();
        Material block = event.getBlock().getType();
        String ore1 = getConfig().getString("1");
        String ore2 = getConfig().getString("2");
        String ore3 = getConfig().getString("3");
        String ore4 = getConfig().getString("4");

        if (amounts1.get(player) != null) {
            amount1 = amounts1.get(player);
            amount2 = amounts2.get(player);
            amount3 = amounts3.get(player);
            amount4 = amounts4.get(player);


            if (block.equals(Material.matchMaterial(ore1)))  {
                amount1++;
                amounts1.put(player, amount1);
                onBlockMineAlert(player, ore1, name, amount1);

            }
            if (block.equals(Material.matchMaterial(ore2))) {
                amount2++;
                amounts2.put(player, amount2);
                onBlockMineAlert(player, ore2, name, amount2);

            }
            if (block.equals(Material.matchMaterial(ore3))){
                amount3++;
                amounts3.put(player, amount3);
                onBlockMineAlert(player, ore3, name, amount3);

            }
            if (block.equals(Material.matchMaterial(ore4))){
                amount4++;
                amounts4.put(player, amount4);
                onBlockMineAlert(player, ore4, name, amount4);

            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        amounts1.put(player, 0);
        amounts2.put(player, 0);
        amounts3.put(player, 0);
        amounts4.put(player, 0);


    }
}
