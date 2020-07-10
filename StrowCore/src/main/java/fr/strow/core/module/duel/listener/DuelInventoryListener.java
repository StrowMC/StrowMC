package fr.strow.core.module.duel.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getCurrentItem() == null) return;
        if (!e.getClickedInventory().getTitle().equals("Duel")) return;
        if (!e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) return;
        if (e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) return;
        e.setCancelled(true);
    }
}
