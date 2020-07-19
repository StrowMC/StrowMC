package fr.strow.core.modules.events.koth.properties;

import com.google.inject.Inject;
import fr.strow.api.game.events.koth.KOTH;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesGrouping;
import fr.strow.api.property.PropertiesHandler;

public class KOTHProperty extends PropertiesGrouping<StrowPlayer> implements KOTH {

    @Inject
    public KOTHProperty(PropertiesHandler propertiesHandler) {
        super(StrowPlayer.class, propertiesHandler);
    }
}
