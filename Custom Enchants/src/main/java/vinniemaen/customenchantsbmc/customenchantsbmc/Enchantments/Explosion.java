package vinniemaen.customenchantsbmc.customenchantsbmc.Enchantments;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import vinniemaen.customenchantsbmc.customenchantsbmc.CustomEnchantsBMC;

import java.util.Random;

public class Explosion extends Enchantment implements Listener {

    public Explosion(String key) {
        super(new NamespacedKey(CustomEnchantsBMC.getPlugin(), key));
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getDamager();
                Player targetentity = (Player) event.getEntity();
                Random r = new Random();
                int i = r.nextInt(100);
                ItemStack mainhand = player.getInventory().getItemInMainHand();
                if (mainhand.containsEnchantment(this)) {
                    if (mainhand.getEnchantmentLevel(this) == 1) {
                        if (i < 10) {
                            player.getWorld().createExplosion(targetentity.getLocation(), 1, false, false);
                            targetentity.setHealth(targetentity.getHealth() - 4);
                        }
                    } else {
                        if (mainhand.getEnchantmentLevel(this) == 2) {
                            if (i < 25) {
                                player.getWorld().createExplosion(targetentity.getLocation(), 2, false);
                                targetentity.setHealth(targetentity.getHealth() - 4);
                            }
                        }
                    }
                }
            }
        }
    }

    public int getId(){
        return 101;
    }

    @Override
    public String getName() {
        return ChatColor.GOLD +"Explosion";
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
