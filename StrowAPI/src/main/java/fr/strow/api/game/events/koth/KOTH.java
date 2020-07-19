package fr.strow.api.game.events.koth;

import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertyFactory;
import fr.strow.api.property.RegistrableProperty;

public interface KOTH extends RegistrableProperty<StrowPlayer, KOTH.Factory> {

    class Factory extends PropertyFactory {

    }
}
