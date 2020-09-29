package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionGroup;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.player.Name;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MotedMemberParameter extends Parameter<StrowPlayer> {

    private final MemberFromSenderFactionParameter memberFromSenderFactionParameter;
    private final PlayerManager playerManager;
    private final Messaging messaging;

    @Inject
    public MotedMemberParameter(MemberFromSenderFactionParameter memberFromSenderFactionParameter, PlayerManager playerManager, Messaging messaging) {
        super("joueur");

        this.memberFromSenderFactionParameter = memberFromSenderFactionParameter;
        this.playerManager = playerManager;
        this.messaging = messaging;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        List<Condition<String>> conditions = new ArrayList<>(memberFromSenderFactionParameter.getConditions(sender));

        conditions.addAll(Arrays.asList(
                new Condition<String>() {
                    @Override
                    public boolean check(String arg) {
                        StrowPlayer strowSender = playerManager.getPlayer(sender);
                        StrowPlayer player = playerManager.getPlayer(arg);

                        return !player.getProperty(Name.class).getName().equals((strowSender.getProperty(Name.class).getName()));
                    }

                    @Override
                    public BaseComponent[] getMessage(String arg) {
                        return messaging.errorMessage("Vous ne pouvez pas vous promouvoir ou vous rétrograder vous même");
                    }
                },
                new Condition<String>() {
                    @Override
                    public boolean check(String arg) {
                        StrowPlayer player = playerManager.getPlayer(arg);

                        int playerRole = player
                                .getProperty(FactionProfile.class)
                                .getProperty(FactionGroup.class)
                                .getRole().getId();

                        int senderRole = playerManager.getPlayer(sender)
                                .getProperty(FactionProfile.class)
                                .getProperty(FactionGroup.class)
                                .getRole().getId();

                        return senderRole > playerRole;
                    }

                    @Override
                    public BaseComponent[] getMessage(String arg) {
                        return messaging.errorMessage("Vous ne pouvez pas promouvoir ou rétrograder un joueur dont le rôle est supérieur ou égal au votre");
                    }
                }
        ));

        return conditions;
    }

    @Override
    public StrowPlayer get(String arg) {
        return memberFromSenderFactionParameter.get(arg);
    }
}
