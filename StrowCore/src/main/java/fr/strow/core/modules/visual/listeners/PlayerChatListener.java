package fr.strow.core.modules.visual.listeners;

import com.google.inject.Inject;
import fr.strow.api.game.faction.Faction;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.faction.FactionPrefix;
import fr.strow.api.game.faction.player.FactionGroup;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.game.permissions.Group;
import fr.strow.api.game.permissions.Role;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.Nickname;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;
import java.util.UUID;

public class PlayerChatListener implements Listener {

    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final Messaging messaging;

    @Inject
    public PlayerChatListener(PlayerManager playerManager, FactionManager factionManager, Messaging messaging) {
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.messaging = messaging;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        StrowPlayer player = playerManager.getPlayer(event.getPlayer().getName());
        Role role = player.getProperty(Group.class).getRole();

        String factionPrefix = "";

        Optional<FactionProfile> optionalFactionProfile = player.getOptionalProperty(FactionProfile.class);
        if (optionalFactionProfile.isPresent()) {
            FactionProfile factionProfile = optionalFactionProfile.get();

            UUID factionUuid = factionProfile
                    .getProperty(FactionUUID.class)
                    .getFactionUuid();
            Faction faction = factionManager.getFaction(factionUuid);

            factionPrefix = String.format("§8[§f%s %s§8] ",
                    faction.getProperty(FactionPrefix.class).getPrefix(),
                    factionProfile.getProperty(FactionGroup.class).getRole().getSymbol());
        }

        String message = String.format(factionPrefix + "§f[%s%s§f] %s%s §8⋙ §f" + event.getMessage(),
                role.getColor(),
                role.getPrefix(),
                role.getColor(),
                player.getProperty(Nickname.class)
                        .getNickname());

        messaging.broadcastMessage(message);
    }
}
