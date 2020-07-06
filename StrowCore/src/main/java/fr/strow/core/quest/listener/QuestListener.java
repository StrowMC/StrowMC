package fr.strow.core.quest.listener;

import fr.strow.core.quest.QuestModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Hokkaydo on 06-07-2020.
 */
public class QuestListener implements Listener {

    private final QuestModule questModule;

    public QuestListener(QuestModule questModule) {
        this.questModule = questModule;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        questModule.processQuests(event);
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        questModule.processQuests(event);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        questModule.processQuests(event);
    }

    @EventHandler
    public void onKillMob(EntityDeathEvent event) {
        questModule.processQuests(event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        questModule.processQuests(event);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        questModule.processQuests(event);
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        questModule.processQuests(event);
    }

}
