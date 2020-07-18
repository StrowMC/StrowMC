/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:52
 */

package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.core.utils.commands.parameters.PlayerParameter;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.*;

public class FactionMemberParameter extends Parameter<StrowPlayer> {

    private final PlayerParameter playerParameter;

    @Inject
    public FactionMemberParameter(PlayerParameter playerParameter) {
        super("joueur");

        this.playerParameter = playerParameter;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        List<Condition<String>> conditions = new ArrayList<>(playerParameter.getConditions(sender));

        conditions.addAll(Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        StrowPlayer strowPlayer = playerParameter.get(o);

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
        return playerParameter.get(arg);
    }
}
