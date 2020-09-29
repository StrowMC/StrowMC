package fr.strow.core.modules.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.faction.Faction;
import fr.strow.api.game.faction.FactionHome;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionSetHomeCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final Messaging messaging;

    @Inject
    public FactionSetHomeCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("sethome")
                .withPermission("faction.sethome")
                .withDescription("Définir le home de votre faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.messaging = messaging;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender);

        Faction faction = factionManager.getFaction(
                strowSender
                        .getProperty(FactionProfile.class)
                        .getProperty(FactionUUID.class)
                        .getFactionUuid()
        );

        if (!faction.getOptionalProperty(FactionHome.class).isPresent()) {
            faction.registerProperty(FactionHome.class);
        }

        Location location = ((Player) sender).getLocation();
        faction.getProperty(FactionHome.class).setHome(location);

        messaging.sendMessage(strowSender, "Le home de votre faction a été fixé en (%f, %f, %f)",
                location.getX(),
                location.getY(),
                location.getZ());
    }
}
