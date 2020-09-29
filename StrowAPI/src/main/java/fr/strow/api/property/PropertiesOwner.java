package fr.strow.api.property;

import java.util.Optional;
import java.util.UUID;

public interface PropertiesOwner<O extends PropertiesOwner<O>> {

    UUID getUniqueId();

    <P extends Property<O>> P getProperty(Class<P> property);

    <P extends Property<O>> Optional<P> getOptionalProperty(Class<P> property);
}
