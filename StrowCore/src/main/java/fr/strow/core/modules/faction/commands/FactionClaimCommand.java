package fr.strow.core.modules.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.faction.Faction;
import fr.strow.api.game.faction.FactionClaims;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import fr.strow.persistence.dao.factions.FactionClaimsDao;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactionClaimCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;

    @Inject
    public FactionClaimCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager) {
        super(CommandDescription.builder()
                .withName("claim")
                .withPermission("faction.claim")
                .withDescription("Claimer un territoire pour votre faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(FreeChunkRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender);

        Chunk claim = ((Player) sender).getLocation().getChunk();

        Faction faction = factionManager.getFaction(
                strowSender
                        .getProperty(FactionProfile.class)
                        .getProperty(FactionUUID.class)
                        .getFactionUuid()
        );

        if (faction.getOptionalProperty(FactionClaims.class).isPresent()) {
            faction.registerProperty(FactionClaims.class);
        }

        faction.getProperty(FactionClaims.class).addClaim(claim);
    }

    static class FreeChunkRequirement extends Requirement {

        private final SenderIsInFactionRequirement senderIsInFactionRequirement;
        private final PlayerManager playerManager;
        private final FactionManager factionManager;
        private final FactionClaimsDao factionClaimsDao;
        private final Messaging messaging;

        @Inject
        public FreeChunkRequirement(SenderIsInFactionRequirement senderIsInFactionRequirement, PlayerManager playerManager, FactionManager factionManager, FactionClaimsDao factionClaimsDao, Messaging messaging) {
            this.senderIsInFactionRequirement = senderIsInFactionRequirement;
            this.playerManager = playerManager;
            this.factionManager = factionManager;
            this.factionClaimsDao = factionClaimsDao;
            this.messaging = messaging;
        }

        @Override
        public List<Condition<CommandSender>> getConditions() {
            List<Condition<CommandSender>> conditions = new ArrayList<>(senderIsInFactionRequirement.getConditions());

            conditions.addAll(Arrays.asList(
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

                            Chunk chunk = ((Player) sender).getLocation().getChunk();

                            return faction.getOptionalProperty(FactionClaims.class)
                                    .map(factionClaims ->
                                            factionClaims.containsClaim(chunk))
                                    .orElse(true);
                        }

                        @Override
                        public BaseComponent[] getMessage(CommandSender sender) {
                            return messaging.errorMessage("Ce territoire appartient déjà à votre faction");
                        }
                    },
                    new Condition<CommandSender>() {
                        @Override
                        public boolean check(CommandSender sender) {
                            Chunk chunk = ((Player) sender).getLocation().getChunk();

                            return factionClaimsDao.isFree(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
                        }

                        @Override
                        public BaseComponent[] getMessage(CommandSender sender) {
                            return messaging.errorMessage("Ce territoire appartient déjà à une autre faction");
                        }
                    }
            ));

            return conditions;
        }
    }
}
