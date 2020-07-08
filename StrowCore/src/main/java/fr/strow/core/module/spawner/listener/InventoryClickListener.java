package fr.strow.core.module.spawner.listener;

import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.player.StrowPlayerImpl;
import fr.strow.core.module.spawner.Spawner;
import fr.strow.core.module.spawner.property.SpawnerProperty;
import fr.strow.core.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Hokkaydo on 08-07-2020.
 */
public class InventoryClickListener implements Listener {

    private final PlayerManager playerManager;
    private final MessageService messageService;

    public InventoryClickListener(PlayerManager playerManager, MessageService messageService) {
        this.playerManager = playerManager;
        this.messageService = messageService;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().getName().equalsIgnoreCase("Spawners")) return;
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) return;

        String name = e.getCurrentItem().getItemMeta().getDisplayName();

        StrowPlayer strowPlayer = playerManager.getPlayer(e.getWhoClicked().getUniqueId());

        SpawnerProperty spawnerProperty = strowPlayer.getOptionalProperty(SpawnerProperty.class).orElseGet(() -> {
            SpawnerProperty prop = new SpawnerProperty();
            ((StrowPlayerImpl) strowPlayer).registerProperty(prop);
            return prop;
        });

        Utils.ifPresentOrElse(Spawner.getByName(name.replace("§c", "").replace("§a", "")),
                spawner -> {
                    if (name.startsWith("§a")) {
                        if (spawnerProperty.inactivateSpawner(spawner)) {
                            messageService.sendMessage(strowPlayer.getUniqueId(), "§aSpawner détruit. \nVous pouvez maintenant le replacer où vous souhaitez");
                        } else {
                            messageService.sendErrorMessage(strowPlayer.getUniqueId(), "Tried to inactivate an inactive spawner : %s", name.replace("§c", "").replace("§a", ""));
                        }
                    } else if (name.startsWith("§c")) {
                        if (spawnerProperty.activateSpawner(spawner, e.getWhoClicked().getLocation())) {
                            e.getWhoClicked().getLocation().getBlock().setType(Material.MOB_SPAWNER);
                            CreatureSpawner creatureSpawner = (CreatureSpawner) e.getWhoClicked().getLocation().getBlock().getState();
                            creatureSpawner.setSpawnedType(spawner.getEntityType());
                            creatureSpawner.update(true, true);
                            messageService.sendMessage(strowPlayer.getUniqueId(), "§aSpawner placé");
                        } else {
                            messageService.sendErrorMessage(strowPlayer.getUniqueId(), "Tried to activate an active spawner : %s", name.replace("§c", "").replace("§a", ""));
                        }
                    } else {
                        messageService.sendErrorMessage(strowPlayer.getUniqueId(), "Spawner %s not found", name.replace("§c", "").replace("§a", ""));
                    }
                },
                () -> messageService.sendErrorMessage(strowPlayer.getUniqueId(), "Spawner %s not found", name.replace("§c", "").replace("§a", ""))
        );
        e.getWhoClicked().closeInventory();
    }
}
