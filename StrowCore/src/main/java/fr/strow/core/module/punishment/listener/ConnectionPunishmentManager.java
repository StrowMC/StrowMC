package fr.strow.core.module.punishment.listener;

import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.core.module.punishment.property.PunishmentProperty;
import fr.strow.core.module.punishment.utils.Punishment;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Hokkaydo on 07-07-2020.
 */
public class ConnectionPunishmentManager {

    public static void checkPunishment(PlayerManager playerManager, UUID player) {
        Optional<PunishmentProperty> punishmentProperty = playerManager.getPlayer(player).getOptionalProperty(PunishmentProperty.class);
        if (!punishmentProperty.isPresent()) return;

        punishmentProperty.get().getMostRecentActivePunishmentByType(Punishment.Type.BAN).ifPresent(punishment -> {
            StrowPlayer moderator = playerManager.getPlayer(punishment.getModeratorId());
            StringBuilder stringBuilder = new StringBuilder();

            LocalDateTime start = LocalDateTime.ofInstant(punishment.getStart().toInstant(), ZoneId.systemDefault());
            LocalDateTime end = LocalDateTime.ofInstant(punishment.getEnd().toInstant(), ZoneId.systemDefault());

            stringBuilder.append("§cVous êtes banni de ce serveur !\n");
            stringBuilder.append("§eBanni le : §a").append(start.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n");
            stringBuilder.append("§eBanni par : §6").append(moderator == null ? punishment.getModeratorName() : moderator).append("\n"); //TODO RankProperty -> getRankFormattedName
            stringBuilder.append("§eRaison : §d").append(punishment.getReason()).append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("§eFin le : §a").append(punishment.isDefinitive() ? "Définitif" : end.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            stringBuilder.append("§6Vous pouvez porter une réclamation sur https://www.strow.fr");

            Bukkit.getPlayer(player).kickPlayer(stringBuilder.toString());
        });
    }

}
