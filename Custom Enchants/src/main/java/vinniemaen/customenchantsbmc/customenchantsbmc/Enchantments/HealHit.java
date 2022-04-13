package vinniemaen.customenchantsbmc.customenchantsbmc.Enchantments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import vinniemaen.customenchantsbmc.customenchantsbmc.CustomEnchantsBMC;

import java.util.Random;

public class HealHit extends Enchantment implements Listener {
    public HealHit(String key) {
        super(new NamespacedKey(CustomEnchantsBMC.getPlugin(), key));
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Random r = new Random();
            int i = r.nextInt(100);
            ItemStack mainhand = player.getInventory().getItemInMainHand();

            if (mainhand.containsEnchantment(this)) {
                if (mainhand.getEnchantmentLevel(this) == 1) {
                        if (i < 10) {
                            player.setHealth(player.getHealth() + 4);

                        } else {
                            if (mainhand.getEnchantmentLevel(this) == 2) {
                                player.setHealth(player.getHealth() + 4);

                            }
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return ChatColor.GOLD + "HealHit";
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
