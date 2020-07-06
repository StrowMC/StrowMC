/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 26/06/2020 10:46
 */

package fr.strow.core.module.faction;

import com.google.inject.Inject;
import fr.strow.api.game.factions.Faction;
import fr.strow.api.game.factions.FactionManager;
import fr.strow.api.game.factions.events.FactionCreateEvent;
import fr.strow.api.game.factions.profile.FactionInvitation;
import fr.strow.api.game.factions.profile.FactionProfile;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.game.players.UniqueId;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.core.module.faction.properties.player.profile.FactionProfileProperty;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FactionManagerImpl implements FactionManager {

    private final PropertiesHandler propertiesHandler;
    private final JavaPlugin plugin;

    @Inject
    public FactionManagerImpl(PropertiesHandler propertiesHandler, JavaPlugin plugin) {
        this.propertiesHandler = propertiesHandler;
        this.plugin = plugin;
    }

    @Override
    public void createFaction(StrowPlayer leader, String name) {
        Optional<FactionProfile> optionalFactionProfile = leader.getOptionalProperty(FactionProfile.class);

        if (optionalFactionProfile.isPresent()) {
            throw new IllegalArgumentException();
        } else {
            FactionCreateEvent event = new FactionCreateEvent(leader.get(UniqueId.class).getUniqueId());
            plugin.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                UUID uuid;
                do {
                    uuid = UUID.randomUUID();
                } while (factions.withUniqueId(uuid).findAny());

                // Set the player faction profile
                leader.registerProperty(propertiesHandler.getProperty(FactionProfileProperty.class));
                FactionProfile profile = new FactionProfileImpl(leader.getUniqueId(), uuid, role);

                Faction faction = new FactionImpl(uuid, new FactionDescriptionImpl(name, description), profile);
                factions.add(faction);
            }
        }
    }

    @Override
    public void createFaction(StrowPlayer leader, String name, String description) {

    }

    @Override
    public boolean factionExists(String name) {
        return false;
    }

    @Override
    public void invitePlayer(StrowPlayer sender, StrowPlayer target) {

    }

    @Override
    public List<FactionInvitation> getPendingInvitations() {
        return null;
    }

    @Override
    public void leaveFaction(StrowPlayer player) {

    }
}
