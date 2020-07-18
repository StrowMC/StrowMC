package fr.strow.core.modules.faction.properties.player;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionInvitation;
import fr.strow.api.property.ImplementationProperty;

import java.util.UUID;

public class FactionInvitationProperty extends ImplementationProperty implements FactionInvitation {

    private UUID sender;
    private UUID faction;

    @Inject
    public FactionInvitationProperty() {
    }

    @Override
    public UUID getSender() {
        return sender;
    }

    @Override
    public UUID getFaction() {
        return faction;
    }

    @Override
    public void build(UUID sender, UUID faction) {
        this.sender = sender;
        this.faction = faction;
    }
}
