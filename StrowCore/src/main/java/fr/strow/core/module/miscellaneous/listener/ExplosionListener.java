package fr.strow.core.module.miscellaneous.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

/**
 * Created by Hokkaydo on 11-07-2020.
 */
public class ExplosionListener implements Listener {

    public static boolean enabled = true;

    @EventHandler
    public void onExplode(BlockExplodeEvent e) {
        if (!enabled) e.setCancelled(true);
    }

}
