package me.votelistener.bmc.bucketmcvotelistener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class BucketMCVoteListener extends JavaPlugin implements Listener {

    String prefix = ChatColor.DARK_AQUA +"["+ ChatColor.AQUA+"Bucket"+ ChatColor.GOLD + "MC" + ChatColor.DARK_AQUA+"]"+" ";

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("vote")){
            sender.sendMessage(prefix + ChatColor.AQUA + "Vote for the server: " + ChatColor.GOLD + "https://votelink.com");
        }
        return super.onCommand(sender, command, label, args);
    }

    @EventHandler
    public void onVote(VotifierEvent event){
        Vote vote = event.getVote();
        String player = vote.getUsername();

        for (Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(prefix + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + player + ChatColor.AQUA + " has voted and received 1 votingkey!");

        }
        if (Bukkit.getServer().getPlayerExact(player) == null){
            return;
        }else{
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + player + "500");
        }
    }
}
