package fr.strow.core.module.faction.properties.player;

import fr.strow.api.game.factions.Faction;
import fr.strow.api.game.factions.profile.FactionInvitation;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.ExplicitInitialisedProperty;
import fr.strow.api.properties.PropertyFactory;

public class FactionInvitationProperty implements ExplicitInitialisedProperty<FactionInvitationProperty.Factory>, FactionInvitation {

    private StrowPlayer sender;
    private Faction faction;

    @Override
    public StrowPlayer getSender() {
        return sender;
    }

    @Override
    public Faction getFaction() {
        return faction;
    }

    public class Factory extends PropertyFactory {

        public void load(StrowPlayer sender, Faction faction) {
            FactionInvitationProperty.this.sender = sender;
            FactionInvitationProperty.this.faction = faction;
        }
    }
}
