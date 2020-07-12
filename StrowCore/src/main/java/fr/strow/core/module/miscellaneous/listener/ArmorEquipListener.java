package fr.strow.core.module.miscellaneous.listener;

import fr.strow.api.StrowPlugin;
import fr.strow.core.module.miscellaneous.command.FarmArmorGiveCommand;
import fr.strow.core.utils.event.armorequipevent.ArmorEquipEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Hokkaydo on 11-07-2020.
 */
public class ArmorEquipListener implements Listener {

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e) {
        if (hasFarmArmorEquiped(e.getPlayer())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!hasFarmArmorEquiped(e.getPlayer())) {
                        this.cancel();
                        return;
                    }
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 2, false, false));
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 2, false, false));
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20, 1, false, false));
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 1, false, false));
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20, 5, false, false));
                }
            }.runTaskTimer(JavaPlugin.getPlugin(StrowPlugin.class), 0L, 20);
        }
    }

    private boolean hasFarmArmorEquiped(Player player) {
        int equals = 0;
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            for (ItemStack armor : FarmArmorGiveCommand.FARM_ARMOR) {
                if (itemStack.equals(armor)) equals++;
            }
        }
        return equals == 4;
    }
}
