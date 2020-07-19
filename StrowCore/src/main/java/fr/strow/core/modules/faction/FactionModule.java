/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:26
 */

package fr.strow.core.modules.faction;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.faction.*;
import fr.strow.api.game.faction.player.*;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.module.StrowModule;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.faction.commands.FactionCommand;
import fr.strow.core.modules.faction.properties.*;
import fr.strow.core.modules.faction.properties.player.FactionInvitationProperty;
import fr.strow.core.modules.faction.properties.player.profile.*;
import me.choukas.commands.EvolvedCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class FactionModule extends StrowModule {

    private final Injector injector;

    @Inject
    public FactionModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandService.class),
                injector.getInstance(PropertiesHandler.class));

        this.injector = injector;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        bindProperty(StrowPlayer.class, FactionProfile.class, FactionProfileProperty.class);
        bindProperty(StrowPlayer.class, FactionInvitation.class, FactionInvitationProperty.class);

        bindProperty(FactionProfile.class, FactionAutoClaiming.class, FactionAutoClaimingProperty.class);
        bindProperty(FactionProfile.class, FactionClaimer.class, FactionClaimerProperty.class);
        bindProperty(FactionProfile.class, FactionGroup.class, FactionGroupProperty.class);
        bindProperty(FactionProfile.class, FactionPower.class, FactionPowerProperty.class);
        bindProperty(FactionProfile.class, FactionUUID.class, FactionUUIDProperty.class);

        bindProperty(Faction.class, FactionChest.class, FactionChestProperty.class);
        bindProperty(Faction.class, FactionClaims.class, FactionClaimsProperty.class);
        bindProperty(Faction.class, FactionDescription.class, FactionDescriptionProperty.class);
        bindProperty(Faction.class, FactionHome.class, FactionHomeProperty.class);
        bindProperty(Faction.class, FactionLeader.class, FactionLeaderProperty.class);
        bindProperty(Faction.class, FactionMembers.class, FactionMembersProperty.class);
        bindProperty(Faction.class, FactionName.class, FactionNameProperty.class);
        bindProperty(Faction.class, FactionPoints.class, FactionPointsProperty.class);
        bindProperty(Faction.class, FactionPrefix.class, FactionPrefixProperty.class);
        bindProperty(Faction.class, FactionWarps.class, FactionWarpsProperty.class);
    }

    @Override
    public List<Class<? extends EvolvedCommand>> getCommands() {
        return Collections.singletonList(
                FactionCommand.class
        );
    }
}
