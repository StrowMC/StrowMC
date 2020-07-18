package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionInvitation;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.core.utils.commands.parameters.ConnectedPlayerParameter;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class GuestParameter extends Parameter<StrowPlayer> {

    private final PlayerManager playerManager;
    private final ConnectedPlayerParameter connectedPlayerParameter;

    @Inject
    public GuestParameter(PlayerManager playerManager, ConnectedPlayerParameter connectedPlayerParameter) {
        super("joueur");

        this.playerManager = playerManager;
        this.connectedPlayerParameter = connectedPlayerParameter;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        List<Condition<String>> conditions = new ArrayList<>(connectedPlayerParameter.getConditions(sender));

        conditions.add(new Condition<String>() {

            @Override
            public boolean check(String o) {
                StrowPlayer player = playerManager.getPlayer(o);

                return player.getOptionalProperty(FactionInvitation.class).isPresent();
            }

            @Override
            public String getMessage(String o) {
                return String.format("%s a déjà reçu une invitation. Attendez qu'il y réponde.", o);
            }
        });

        return conditions;
    }

    @Override
    public StrowPlayer get(String arg) {
        return null;
    }
}
