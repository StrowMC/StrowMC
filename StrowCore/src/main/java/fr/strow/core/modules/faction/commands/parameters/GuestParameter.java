package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionInvitation;
import fr.strow.api.game.player.Name;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.utils.commands.parameters.ConnectedPlayerParameter;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuestParameter extends Parameter<StrowPlayer> {

    private final PlayerManager playerManager;
    private final ConnectedPlayerParameter connectedPlayerParameter;
    private final Messaging messaging;

    @Inject
    public GuestParameter(PlayerManager playerManager, ConnectedPlayerParameter connectedPlayerParameter, Messaging messaging) {
        super("joueur");

        this.playerManager = playerManager;
        this.connectedPlayerParameter = connectedPlayerParameter;
        this.messaging = messaging;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        List<Condition<String>> conditions = new ArrayList<>(connectedPlayerParameter.getConditions(sender));

        conditions.addAll(Arrays.asList(
                new Condition<String>() {
                    @Override
                    public boolean check(String arg) {
                        StrowPlayer strowSender = playerManager.getPlayer(sender);
                        StrowPlayer player = playerManager.getPlayer(arg);

                        return !player.getProperty(Name.class).getName().equals((strowSender.getProperty(Name.class).getName()));
                    }

                    @Override
                    public BaseComponent getMessage(String arg) {
                        return messaging.errorMessage("Vous ne pouvez pas vous inviter vous-même");
                    }
                },
                new Condition<String>() {

                    @Override
                    public boolean check(String arg) {
                        StrowPlayer player = playerManager.getPlayer(arg);

                        return player.getOptionalProperty(FactionInvitation.class).isPresent();
                    }

                    @Override
                    public BaseComponent getMessage(String arg) {
                        return messaging.errorMessage("%s a déjà reçu une invitation. Attendez qu'il y réponde.", arg);
                    }
                }));

        return conditions;
    }

    @Override
    public StrowPlayer get(String arg) {
        return null;
    }
}
