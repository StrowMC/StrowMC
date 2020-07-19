package fr.strow.core.modules.faction.properties.player;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionInvitation;
import fr.strow.api.property.ImplementationProperty;

import java.util.UUID;

public class FactionInvitationProperty implements FactionInvitation, ImplementationProperty<FactionInvitation> {

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
    public void onRegister(UUID uuid, Factory factory) {
        sender = factory.getSender();
        faction = factory.getFaction();
    }
}
