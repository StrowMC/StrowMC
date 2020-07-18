package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectedMemberFromSenderFactionParameter extends Parameter<StrowPlayer> {

    private final FactionConnectedMemberParameter factionMemberParameter;
    private final PlayerManager playerManager;

    @Inject
    public ConnectedMemberFromSenderFactionParameter(FactionConnectedMemberParameter factionMemberParameter, PlayerManager playerManager) {
        super("joueur");

        this.factionMemberParameter = factionMemberParameter;
        this.playerManager = playerManager;
    }


    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        List<Condition<String>> conditions = new ArrayList<>(factionMemberParameter.getConditions(sender));

        conditions.addAll(Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        return factionMemberParameter.get(o)
                                .getProperty(FactionProfile.class)
                                .getProperty(FactionUUID.class)
                                .getFactionUuid()
                                .equals(playerManager.getPlayer(((Player) sender).getUniqueId())
                                        .getProperty(FactionProfile.class)
                                        .getProperty(FactionUUID.class)
                                        .getFactionUuid());
                    }

                    @Override
                    public String getMessage(String o) {
                        return "Ce joueur n'appartient pas à votre faction";
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
