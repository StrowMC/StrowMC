package fr.strow.api.properties;

import java.util.UUID;

public interface ImplicitInitialisedProperty extends Property {

    void load(UUID uuid);
}
