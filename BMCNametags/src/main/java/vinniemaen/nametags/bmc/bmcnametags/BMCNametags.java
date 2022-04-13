package vinniemaen.nametags.bmc.bmcnametags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BMCNametags extends JavaPlugin {

    String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Bucket" + ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA + "]" + " ";


    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "BMCNametags Enabled");
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "BMCNametags Disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nametag")){
            if (sender.hasPermission("nametag.give")){
                if (args.length > 1){
                    String target = args[0];
                    Player targetplayer = Bukkit.getPlayer(target);
                    String nametag = args[1].replace('&','§');

                    ItemStack nametagitem = new ItemStack(Material.NAME_TAG,1);
                    ItemMeta nametagmeta = nametagitem.getItemMeta();
                    ArrayList<String> ntlore = new ArrayList<>();

                    nametagmeta.setDisplayName(ChatColor.AQUA + "Prefix Nametag");

                    ntlore.add(" ");
                    ntlore.add(ChatColor.GREEN + "Right click to apply prefix:");
                    ntlore.add(nametag);
                    ntlore.add(" ");
                    ntlore.add(ChatColor.RED + "When applying this, your current Nametag Prefix will be replaced.");

                    nametagmeta.setLore(ntlore);

                    nametagitem.setItemMeta(nametagmeta);

                    targetplayer.getInventory().addItem(nametagitem);

                }else{
                    sender.sendMessage(prefix + ChatColor.RED + "Wrong usage! /nametag <Player> <Prefix>");
                }
            }else{
                sender.sendMessage(prefix + ChatColor.RED + "You don't have permission to use this command!");
            }
        }

        return false;
    }
}
