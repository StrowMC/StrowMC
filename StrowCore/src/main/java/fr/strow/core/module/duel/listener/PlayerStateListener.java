package fr.strow.core.module.duel.listener;

import fr.strow.core.module.duel.util.DuelGroupManager;
import fr.strow.core.module.duel.util.DuelManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class PlayerStateListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        DuelGroupManager.getPlayerGroup(e.getPlayer()).ifPresent(g -> DuelGroupManager.leaveGroup(e.getPlayer(), g));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        DuelGroupManager.getPlayerGroup(e.getPlayer()).ifPresent(g -> {
            if (DuelManager.isInDuel(g) && !e.getMessage().startsWith("/duel leave")) {
                e.setCancelled(true);
            }
        });
    }
}
