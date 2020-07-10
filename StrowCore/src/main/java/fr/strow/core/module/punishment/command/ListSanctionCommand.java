package fr.strow.core.module.punishment.command;


import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.core.module.punishment.property.PunishmentProperty;
import fr.strow.core.module.punishment.utils.Punishment;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class ListSanctionCommand extends EvolvedCommand {

    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();

    private final PlayerManager playerManager;

    public ListSanctionCommand(PlayerManager playerManager) {
        super(
                CommandDescription
                        .builder()
                        .withName("listsanction")
                        .withAliases("ls")
                        .withPermission("strow.sanction")
                        .withDescription("Permet de consulter les sanctions d'un joueur")
                        .build()
        );
        this.playerManager = playerManager;
    }

    @Override
    protected void define() {
        addParam(PLAYER_PARAMETER, true);
    }

    @Override
    protected void execute(CommandSender sender) {
        Player target = readArg();

        StrowPlayer strowPlayer = playerManager.getPlayer(target.getUniqueId());

        Inventory inventory = Bukkit.createInventory(null, 54, "§eSanctions - " + target.getName());
        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        fillerMeta.setDisplayName("§7");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < 9; i++) {
            inventory.addItem(filler);
        }

        for (int i = 0; i < 46; i += 9) {
            inventory.setItem(i, filler);
        }
        for (int i = 8; i < 55; i += 9) {
            inventory.setItem(i, filler);
        }
        for (int i = 45; i < 55; i++) {
            inventory.setItem(i, filler);
        }

        strowPlayer.getOptionalProperty(PunishmentProperty.class).ifPresent(property -> {
            ItemStack book = new ItemStack(Material.BOOK);
            ItemMeta meta = book.getItemMeta();

            List<Punishment> list = property.getOrderedByDecreasingDate();
            AtomicInteger amount = new AtomicInteger(list.size());
            list.forEach(punishment -> {
                String name = LocalDateTime.ofInstant(punishment.getStart().toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss"));
                meta.setDisplayName("§e#" + amount.getAndDecrement() + " - " + (punishment.isActive() ? "§a" : "§c") + name);
                book.setItemMeta(meta);
                inventory.addItem(book);
            });
        });

        ((Player) sender).openInventory(inventory);
    }
}
