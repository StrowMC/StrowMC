package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.core.utils.commands.parameters.ConnectedPlayerParameter;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FactionConnectedMemberParameter extends Parameter<StrowPlayer> {

    private final ConnectedPlayerParameter connectedPlayerParameter;

    @Inject
    public FactionConnectedMemberParameter(ConnectedPlayerParameter connectedPlayerParameter) {
        super("joueur");

        this.connectedPlayerParameter = connectedPlayerParameter;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        List<Condition<String>> conditions = new ArrayList<>(connectedPlayerParameter.getConditions(sender));

        conditions.addAll(Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        StrowPlayer strowPlayer = connectedPlayerParameter.get(o);

                        return strowPlayer.getOptionalProperty(FactionProfile.class).isPresent();
                    }

                    @Override
                    public String getMessage(String o) {
                        return "Ce joueur n'est membre d'aucune faction";
                    }
                }
        ));

        return conditions;
    }

    @Override
    public StrowPlayer get(String arg) {
        return connectedPlayerParameter.get(arg);
    }
}
