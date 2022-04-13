package vinniemaen.customenchantsbmc.customenchantsbmc.Enchantments;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import vinniemaen.customenchantsbmc.customenchantsbmc.CustomEnchantsBMC;

import java.util.Random;

public class EuroKill extends Enchantment implements Listener {
    public EuroKill(String key) {
        super(new NamespacedKey(CustomEnchantsBMC.getPlugin(), key));
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event){
        if (event.getEntity().getKiller() != null) {
            event.getEntity();
            Player killer = event.getEntity().getKiller();
            ItemStack mainhand = killer.getInventory().getItemInMainHand();
            Random r = new Random();
            int i = r.nextInt(100);

            if (mainhand.containsEnchantment(this)) {
                if (mainhand.getEnchantmentLevel(this) == 1) {
                    if (i < 10) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + killer.getName() + " 50");

                    }
                } else {
                    if (mainhand.getEnchantmentLevel(this) == 2) {
                        if (i < 20) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + killer.getName() + " 50");

                        }
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return ChatColor.GOLD + "EuroKill";
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }
}
