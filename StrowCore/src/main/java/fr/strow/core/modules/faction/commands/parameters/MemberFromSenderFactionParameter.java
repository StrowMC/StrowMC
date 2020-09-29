package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.*;

public class MemberFromSenderFactionParameter extends Parameter<StrowPlayer> {

    private final FactionMemberParameter factionMemberParameter;
    private final PlayerManager playerManager;
    private final Messaging messaging;

    @Inject
    public MemberFromSenderFactionParameter(FactionMemberParameter factionMemberParameter, PlayerManager playerManager, Messaging messaging) {
        super("joueur");

        this.factionMemberParameter = factionMemberParameter;
        this.playerManager = playerManager;
        this.messaging = messaging;
    }


    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        List<Condition<String>> conditions = new ArrayList<>(factionMemberParameter.getConditions(sender));

        conditions.addAll(Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String arg) {
                        return factionMemberParameter.get(arg)
                                .getProperty(FactionProfile.class)
                                .getProperty(FactionUUID.class)
                                .getFactionUuid()
                                .equals(playerManager.getPlayer(sender)
                                        .getProperty(FactionProfile.class)
                                        .getProperty(FactionUUID.class)
                                        .getFactionUuid());
                    }

                    @Override
                    public BaseComponent[] getMessage(String arg) {
                        return messaging.errorMessage("Ce joueur n'appartient pas à votre faction");
                    }
                }
        ));

        return conditions;
    }

    @Override
    public StrowPlayer get(String arg) {
        return factionMemberParameter.get(arg);
    }
}
