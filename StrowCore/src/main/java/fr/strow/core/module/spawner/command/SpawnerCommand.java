package fr.strow.core.module.spawner.command;

import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.player.StrowPlayerImpl;
import fr.strow.core.module.spawner.Spawner;
import fr.strow.core.module.spawner.property.SpawnerProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Hokkaydo on 08-07-2020.
 */
public class SpawnerCommand extends EvolvedCommand {

    private final PlayerManager playerManager;
    private final MessageService messageService;

    public SpawnerCommand(PlayerManager playerManager, MessageService messageService) {
        super(
                CommandDescription
                        .builder()
                        .withDescription("Permet de consulter la liste de ses spawners")
                        .withName("spawner")
                        .build()
        );
        this.playerManager = playerManager;
        this.messageService = messageService;
    }

    @Override
    protected void execute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cCommande réservée aux joueurs");
            return;
        }
        StrowPlayer strowPlayer = playerManager.getPlayer(((Player) sender).getUniqueId());

        SpawnerProperty spawnerProperty = strowPlayer.getOptionalProperty(SpawnerProperty.class).orElseGet(() -> {
            SpawnerProperty prop = new SpawnerProperty();
            ((StrowPlayerImpl) strowPlayer).registerProperty(prop);
            return prop;
        });

        Inventory inventory = Bukkit.createInventory(null, 54, "Spawners");
        ItemStack itemStack = new ItemStack(Material.MOB_SPAWNER);
        ItemMeta meta = itemStack.getItemMeta();
        for (Spawner spawner : spawnerProperty.getActiveSpawners()) {
            meta.setDisplayName("§a" + spawner.getName());
            itemStack.setItemMeta(meta);
            inventory.addItem(itemStack);
        }
        for (Spawner spawner : spawnerProperty.getInactiveSpawners()) {
            meta.setDisplayName("§c" + spawner.getName());
            itemStack.setItemMeta(meta);
            inventory.addItem(itemStack);
        }
    }
}
