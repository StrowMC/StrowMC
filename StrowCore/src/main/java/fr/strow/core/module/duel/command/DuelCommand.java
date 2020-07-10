package fr.strow.core.module.duel.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.duel.command.parameter.InventoryType;
import fr.strow.core.module.duel.util.Duel;
import fr.strow.core.module.duel.util.DuelGroup;
import fr.strow.core.module.duel.util.DuelGroupManager;
import fr.strow.core.module.duel.util.DuelManager;
import fr.strow.core.utils.Utils;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelCommand extends EvolvedCommand {

    private static final Parameter<SetInventoryCommand.InventoryType> inventoryTypeParameter = new InventoryType();
    private final Injector injector;

    @Inject
    public DuelCommand(Injector injector) {
        super(
                CommandDescription
                        .builder()
                        .withName("duel")
                        .withDescription("Permet d'interagir avec les duels")
                        .withPermission("duel.admin")
                        .build()
        );
        this.injector = injector;
        define();
    }

    @Override
    protected void define() {
        addSubCommand(injector.getInstance(SetSpawnCommand.class));
        addSubCommand(injector.getInstance(SetInventoryCommand.class));
        addSubCommand(injector.getInstance(DuelInviteCommand.class));

        addParam(inventoryTypeParameter, false);
    }

    @Override
    protected void execute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cVeuillez exécuter cette action en jeu");
            return;
        }
        Player player = (Player) sender;
        Optional<SetInventoryCommand.InventoryType> type = readOptionalArg();
        Utils.ifPresentOrElse(
                DuelGroupManager.getPlayerGroup(player),
                g -> {
                    if (!DuelManager.isInDuel(g)) return;
                    if (!g.getLeader().equals(player.getUniqueId())) {
                        injector.getInstance(MessageService.class).sendMessage(player.getUniqueId(), "§cVous n'êtes pas le chef de votre groupe");
                        return;
                    }
                    Utils.ifPresentOrElse(type,
                            inventoryType -> Utils.ifPresentOrElse(
                                    Duel.Type.getByAmount(g.getPlayers().size()),
                                    t -> {
                                        if (DuelManager.setRequestingDuelGroup(t, g, inventoryType)) {
                                            injector.getInstance(MessageService.class).sendMessage(player.getUniqueId(), "§aVous êtes en attente pour un duel %s. Vous pouvez quitter à tout moment avec /duel leave", t.getName());
                                        } else {
                                            injector.getInstance(MessageService.class).sendMessage(player.getUniqueId(), "§cCette arène est déjà occupée");
                                        }
                                    },
                                    () -> injector.getInstance(MessageService.class).sendMessage(
                                            player.getUniqueId(),
                                            "§cVotre groupe comporte un nombre inexacte de joueur. Veuillez vous conformer aux tailles suivantes : 1, 2, 8")),
                            () -> openDuelInventory(player)
                    );
                },
                () -> {
                    if (!DuelManager.isInDuel(player)) return;
                    Utils.ifPresentOrElse(type,
                            inventoryType -> {
                                DuelGroup duelGroup = new DuelGroup(player.getUniqueId());
                                DuelGroupManager.addGroup(duelGroup);
                                if (DuelManager.setRequestingDuelGroup(Duel.Type.ONEVONE, duelGroup, inventoryType)) {
                                    injector.getInstance(MessageService.class).sendMessage(player.getUniqueId(), "§aVous êtes en attente pour un duel %s. Vous pouvez quitter à tout moment avec /duel leave", Duel.Type.ONEVONE.getName());
                                } else {
                                    injector.getInstance(MessageService.class).sendMessage(player.getUniqueId(), "§cCette arène est déjà occupée");
                                }
                            },
                            () -> openDuelInventory(player)
                    );
                });
    }

    private short getDuelColorData(Duel.Type type) {
        AtomicReference<Short> data = new AtomicReference<>((short) 15);
        DuelManager.getDuel(type).ifPresent(duel -> {
            if (duel.getInventoryType() == SetInventoryCommand.InventoryType.PRACTICE) data.set((short) 1);
            if (duel.getInventoryType() == SetInventoryCommand.InventoryType.GAMBLING) data.set((short) 10);
            if (duel.getInventoryType() == SetInventoryCommand.InventoryType.PRACTICE) data.set((short) 14);
        });
        return data.get();
    }

    private void openDuelInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Duel");

        ItemStack oneVOne = new ItemStack(Material.STAINED_GLASS_PANE, 1, getDuelColorData(Duel.Type.ONEVONE));
        ItemMeta oneVOneMeta = oneVOne.getItemMeta();
        oneVOneMeta.setDisplayName("§e1v1");
        if (DuelManager.getDuel(Duel.Type.ONEVONE).isPresent()) {
            oneVOneMeta.setLore(Collections.singletonList("§e" + DuelManager.getDuel(Duel.Type.ONEVONE).get().getInventoryType().getName()));
        }
        oneVOne.setItemMeta(oneVOneMeta);

        ItemStack twoVTwo = new ItemStack(Material.STAINED_GLASS_PANE, 2, getDuelColorData(Duel.Type.TWOVTWO));
        ItemMeta twoVTwoMeta = twoVTwo.getItemMeta();
        twoVTwoMeta.setDisplayName("§e2v2");
        if (DuelManager.getDuel(Duel.Type.TWOVTWO).isPresent()) {
            twoVTwoMeta.setLore(Collections.singletonList("§e" + DuelManager.getDuel(Duel.Type.TWOVTWO).get().getInventoryType().getName()));
        }
        twoVTwo.setItemMeta(twoVTwoMeta);

        ItemStack eightVEight = new ItemStack(Material.STAINED_GLASS_PANE, 2, getDuelColorData(Duel.Type.EIGHTVEIGHT));
        ItemMeta eightVEightMeta = eightVEight.getItemMeta();
        eightVEightMeta.setDisplayName("§e8v8");
        if (DuelManager.getDuel(Duel.Type.EIGHTVEIGHT).isPresent()) {
            eightVEightMeta.setLore(Collections.singletonList("§e" + DuelManager.getDuel(Duel.Type.EIGHTVEIGHT).get().getInventoryType().getName()));
        }
        eightVEight.setItemMeta(eightVEightMeta);

        inventory.setItem(11, oneVOne);
        inventory.setItem(13, twoVTwo);
        inventory.setItem(15, eightVEight);

        player.openInventory(inventory);
    }
}
