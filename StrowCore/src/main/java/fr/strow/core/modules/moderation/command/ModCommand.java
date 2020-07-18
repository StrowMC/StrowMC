package fr.strow.core.modules.moderation.command;

import com.google.inject.Inject;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.moderation.ModerationModule;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

/**
 * Created by Hokkaydo on 09-07-2020.
 */
public class ModCommand extends EvolvedCommand {

    private final PlayerManager playerManager;
    private final Messaging messaging;

    @Inject
    public ModCommand(PlayerManager playerManager, Messaging messaging) {
        super(
                CommandDescription
                        .builder()
                        .withName("mod")
                        .withDescription("Permet d'obtenir les outils de modération")
                        .withPermission("strow.mod")
                        .withAliases("sm", "staffmod", "staff")
                        .build()
        );
        this.playerManager = playerManager;
        this.messaging = messaging;
    }

    @Override
    protected void execute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande");
            return;
        }
        Player player = (Player) sender;
        if (ModerationModule.isInModMode(player.getUniqueId())) {
            player.setGameMode(GameMode.SURVIVAL);
            player.getActivePotionEffects().forEach(p -> player.removePotionEffect(p.getType()));
            Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));
            messaging.sendMessage(playerManager.getPlayer(player.getUniqueId()), "%s §cTu n'est plus en mode staff.", ModerationModule.PREFIX);
            player.getInventory().clear();
            player.teleport(player.getWorld().getSpawnLocation());
            ModerationModule.removeMod(player.getUniqueId());
            return;
        }
        ItemStack randomTP = new ItemStack(Material.RECORD_3);
        ItemMeta randomTPMeta = randomTP.getItemMeta();
        randomTPMeta.setDisplayName("§5Téléporation à un joueur aléatoire");
        randomTPMeta.setLore(Collections.singletonList("§dClic droit pour être téléporté à un joueur aléatoirement"));
        randomTP.setItemMeta(randomTPMeta);

        ItemStack freeze = new ItemStack(Material.BLAZE_ROD);
        ItemMeta freezeMeta = freeze.getItemMeta();
        freezeMeta.setDisplayName("§3Freeze un joueur");
        freezeMeta.setLore(Collections.singletonList("§bClic droit sur un joueur pour le freeze"));
        freeze.setItemMeta(freezeMeta);

        ItemStack invsee = new ItemStack(Material.BOOK);
        ItemMeta invseeMeta = invsee.getItemMeta();
        invseeMeta.setDisplayName("§7Regarder l'inventaire");
        invseeMeta.setLore(Collections.singletonList("§8Clic droit sur un joueur pour regarder son inventaire"));
        invsee.setItemMeta(invseeMeta);

        ItemStack invisibility = new ItemStack(Material.INK_SACK, 1, (short) 10);
        ItemMeta invisibilityMeta = invisibility.getItemMeta();
        invisibilityMeta.setDisplayName("§cInvisibilité");
        invisibilityMeta.setLore(Collections.singletonList("§6Clic droit pour désactiver le vanish"));
        invisibility.setItemMeta(invisibilityMeta);

        ItemStack leaveMod = new ItemStack(Material.INK_SACK, 1, (short) 14);
        ItemMeta leaveModMeta = leaveMod.getItemMeta();
        leaveModMeta.setDisplayName("§4Quitter le mode staff");
        leaveModMeta.setLore(Collections.singletonList("§cClic droit pour quitter le mode staff"));
        leaveMod.setItemMeta(leaveModMeta);

        player.getInventory().setItem(0, randomTP);
        player.getInventory().setItem(1, freeze);
        player.getInventory().setItem(2, invisibility);
        player.getInventory().setItem(3, invsee);
        player.getInventory().setItem(8, leaveMod);

        player.setGameMode(GameMode.CREATIVE);
        player.getActivePotionEffects().forEach(p -> player.removePotionEffect(p.getType()));
        Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(player));
        player.teleport(player.getWorld().getSpawnLocation());
        messaging.sendMessage(playerManager.getPlayer(player.getUniqueId()), "%s §cTu est désormais en mode staff.", ModerationModule.PREFIX);


        ModerationModule.addMod(player.getUniqueId());
    }
}
