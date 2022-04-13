package vinniemaen.customenchantsbmc.customenchantsbmc.Enchantments;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import vinniemaen.customenchantsbmc.customenchantsbmc.CustomEnchantsBMC;

import java.util.Random;

public class Runaway extends Enchantment implements Listener {

    public Runaway(String key) {
        super(new NamespacedKey(CustomEnchantsBMC.getPlugin(), key));
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {

                Player player = (Player) event.getDamager();
                Player targetentity = (Player) event.getEntity();
                double damage = targetentity.getLastDamage();

                Random r = new Random();
                int i = r.nextInt(100);

                ItemStack chestplate = targetentity.getInventory().getBoots();

                if (chestplate != null && chestplate.containsEnchantment(this)) {
                    if (chestplate.getEnchantmentLevel(this) == 1) {
                        if (i < 10) {
                            targetentity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100,0));

                        }
                    } else {
                        if (chestplate.getEnchantmentLevel(this) == 2) {
                            if (i < 20) {
                                targetentity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100,0));

                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return ChatColor.GOLD +"Runaway";
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
        return EnchantmentTarget.ARMOR_FEET;
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
