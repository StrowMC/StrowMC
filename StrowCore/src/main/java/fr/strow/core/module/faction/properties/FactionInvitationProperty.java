package fr.strow.core.module.faction.properties;

import fr.strow.api.game.factions.Faction;
import fr.strow.api.game.factions.player.FactionInvitation;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.OptionalProperty;

public class FactionInvitationProperty implements OptionalProperty<FactionInvitationFactory>, FactionInvitation {

    private StrowPlayer sender;
    private Faction faction;

    @Override
    public void init(FactionInvitationFactory factory) {
        sender = factory.getSender();
        faction = factory.getFaction();
    }

    @Override
    public StrowPlayer getSender() {
        return sender;
    }

    @Override
    public Faction getFaction() {
        return faction;
    }
}
