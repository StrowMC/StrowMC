package fr.strow.core.utils.event.armorequipevent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

import java.util.Arrays;

public class DispenserArmorListener implements Listener {

    @EventHandler
    public void dispenseArmorEvent(BlockDispenseEvent event) {
        ArmorType type = ArmorType.matchType(event.getItem());
        if (type != null) {
            Player player = null;
            for (Entity entity : event.getBlock().getLocation().getWorld().getNearbyEntities(event.getBlock().getLocation(), 2, 2, 2)) {
                if (entity instanceof Player) {
                    if (Arrays.asList(((Player) entity).getInventory().getArmorContents()).contains(event.getItem())) {
                        player = (Player) entity;
                    }
                }
            }
            if (player != null) {
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.DISPENSER, type, null, event.getItem());
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    event.setCancelled(true);
                }
            }
        }
    }
}