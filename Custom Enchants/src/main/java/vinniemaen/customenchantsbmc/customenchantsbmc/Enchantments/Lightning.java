package vinniemaen.customenchantsbmc.customenchantsbmc.Enchantments;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import vinniemaen.customenchantsbmc.customenchantsbmc.CustomEnchantsBMC;

import java.util.Random;

public class Lightning extends Enchantment implements Listener {

    public Lightning(String key) {
        super(new NamespacedKey(CustomEnchantsBMC.getPlugin(), key));
    }

    @EventHandler
    public void onPlayerHit(ProjectileHitEvent event){
        if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof  Arrow) {

            Player player = (Player) event .getEntity().getShooter();
            Entity arrow = event.getEntity();
            Player targetentity = (Player) event.getHitEntity();
            Random r = new Random();
            int i = r.nextInt(100);
            ItemStack mainhand = player.getInventory().getItemInMainHand();
            if (mainhand.containsEnchantment(this)) {
                if (mainhand.getEnchantmentLevel(this) == 1) {
                    if (i < 10) {
                        targetentity.getWorld().strikeLightning(arrow.getLocation());
                        if (targetentity.getHealth() > 4) {
                            targetentity.setHealth(targetentity.getHealth() - 4);
                        }
                    }
                }else{
                    if (mainhand.getEnchantmentLevel(this) == 2) {

                        if (i < 20) {
                            targetentity.getWorld().strikeLightning(arrow.getLocation());
                            if (targetentity.getHealth() > 4) {
                                targetentity.setHealth(targetentity.getHealth() - 4);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return ChatColor.GOLD +"Lightning";
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
        return EnchantmentTarget.BOW;
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
