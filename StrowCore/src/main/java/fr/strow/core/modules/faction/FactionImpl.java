package fr.strow.core.modules.faction;

import com.google.inject.Inject;
import fr.strow.api.game.faction.Faction;
import fr.strow.api.game.faction.FactionMembers;
import fr.strow.api.property.PropertiesEntity;
import fr.strow.api.property.PropertiesHandler;

import java.util.Collection;
import java.util.UUID;

public class FactionImpl extends PropertiesEntity<Faction> implements Faction {

    @Inject
    public FactionImpl(PropertiesHandler propertiesHandler) {
        super(Faction.class, propertiesHandler);
    }

    @Override
    public Collection<UUID> getRecipients() {
        return getProperty(FactionMembers.class).getMembers();
    }
}
