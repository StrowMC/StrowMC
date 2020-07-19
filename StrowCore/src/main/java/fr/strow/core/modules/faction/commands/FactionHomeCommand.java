package fr.strow.core.modules.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.faction.Faction;
import fr.strow.api.game.faction.FactionChest;
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
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionHomeCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final Messaging messaging;

    @Inject
    public FactionHomeCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("home")
                .withPermission("faction.home")
                .withDescription("Se téléporter au home de votre faction")
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

        Location location = faction.getProperty(FactionHome.class).getHome();

        ((Player) sender).teleport(location);

        messaging.sendMessage(strowSender, "Téléporté au home de votre faction");
    }

    static class SenderFactionHasHomeRequirement extends Requirement {

        private final SenderIsInFactionRequirement senderIsInFactionRequirement;
        private final PlayerManager playerManager;
        private final FactionManager factionManager;
        private final Messaging messaging;

        @Inject
        public SenderFactionHasHomeRequirement(SenderIsInFactionRequirement senderIsInFactionRequirement, PlayerManager playerManager, FactionManager factionManager, Messaging messaging) {
            this.senderIsInFactionRequirement = senderIsInFactionRequirement;
            this.playerManager = playerManager;
            this.factionManager = factionManager;
            this.messaging = messaging;
        }

        @Override
        public List<Condition<CommandSender>> getConditions() {
            List<Condition<CommandSender>> conditions = senderIsInFactionRequirement.getConditions();

            conditions.add(
                    new Condition<CommandSender>() {
                        @Override
                        public boolean check(CommandSender sender) {
                            StrowPlayer strowSender = playerManager.getPlayer(sender);

                            Faction faction = factionManager.getFaction(
                                    strowSender
                                            .getProperty(FactionProfile.class)
                                            .getProperty(FactionUUID.class)
                                            .getFactionUuid()
                            );

                            return faction.getOptionalProperty(FactionChest.class).isPresent();
                        }

                        @Override
                        public BaseComponent getMessage(CommandSender o) {
                            return messaging.errorMessage("Votre faction n'a pas de home");
                        }
                    }
            );

            return conditions;
        }
    }
}
