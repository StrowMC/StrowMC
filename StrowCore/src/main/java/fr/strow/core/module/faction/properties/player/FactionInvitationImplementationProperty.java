package fr.strow.core.module.faction.properties.player;

import fr.strow.api.game.factions.Faction;
import fr.strow.api.game.factions.profile.FactionInvitation;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.FactoringImplementationProperty;
import fr.strow.api.properties.PropertyFactory;

public class FactionInvitationImplementationProperty implements FactoringImplementationProperty<FactionInvitationImplementationProperty.Factory>, FactionInvitation {

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
            FactionInvitationImplementationProperty.this.sender = sender;
            FactionInvitationImplementationProperty.this.faction = faction;
        }
    }
}
