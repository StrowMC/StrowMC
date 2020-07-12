package fr.strow.core.module.miscellaneous.listener;

import com.google.inject.Inject;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.miscellaneous.configuration.MiscellaneousConfiguration;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Hokkaydo on 12-07-2020.
 */
public class FarmSwordListener implements Listener {

    private final PlayerManager playerManager;
    private final MessageService messageService;

    @Inject
    public FarmSwordListener(PlayerManager playerManager, MessageService messageService) {
        this.playerManager = playerManager;
        this.messageService = messageService;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || e.getEntity() instanceof Player) return;
        int money = 0;
        switch (e.getEntity().getType()) {
            case PIG:
                money = MiscellaneousConfiguration.PIG_MONEY;
                break;
            case COW:
                money = MiscellaneousConfiguration.COW_MONEY;
                break;
            case ZOMBIE:
                money = MiscellaneousConfiguration.ZOMBIE_MONEY;
                break;
            case SPIDER:
                money = MiscellaneousConfiguration.SPIDER_MONEY;
                break;
            case SKELETON:
                money = MiscellaneousConfiguration.SKELETON_MONEY;
                break;
            case PIG_ZOMBIE:
                money = MiscellaneousConfiguration.PIGMAN_MONEY;
                break;
            case IRON_GOLEM:
                money = MiscellaneousConfiguration.GOLEM_MONEY;
                break;
            case CREEPER:
                money = MiscellaneousConfiguration.CREEPER_MONEY;
                break;
            case BLAZE:
                money = MiscellaneousConfiguration.BLAZE_MONEY;
                break;
        }
        if (money != 0) {
            //playerManager.getPlayer(e.getDamager().getUniqueId()).getOptionalProperty(); TODO add money
            messageService.sendMessage(e.getDamager().getUniqueId(), "§aVous avez gagné " + money + "$");
            ((Player) e.getDamager()).playSound(e.getDamager().getLocation(), Sound.ARROW_HIT, 10, 10);
        }
    }
}
