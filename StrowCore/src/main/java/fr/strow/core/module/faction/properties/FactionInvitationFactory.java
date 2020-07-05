package fr.strow.core.module.faction.properties;

import fr.strow.api.game.factions.Faction;
import fr.strow.api.game.factions.player.FactionInvitation;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.PropertyFactory;

public class FactionInvitationFactory extends PropertyFactory implements FactionInvitation {

    private final StrowPlayer sender;
    private final Faction faction;

    public FactionInvitationFactory(StrowPlayer sender, Faction faction) {
        this.sender = sender;
        this.faction = faction;
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
