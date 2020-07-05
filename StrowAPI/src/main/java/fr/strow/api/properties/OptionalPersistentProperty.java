package fr.strow.api.properties;

import java.util.UUID;

public interface OptionalPersistentProperty extends PersistentProperty {

    boolean has(UUID uuid);
}
