package vinniemaen.vanish.vanish;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Vanish extends JavaPlugin {

    ArrayList<Player> invisible_list = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Vanish+ Enabled");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Vanish+ Enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("v") & command.getName().equalsIgnoreCase("vanish")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (invisible_list.contains(player)){

                }
            }

        }

        return false;
    }
}
