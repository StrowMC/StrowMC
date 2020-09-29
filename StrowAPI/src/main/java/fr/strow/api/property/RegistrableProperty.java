package fr.strow.api.property;

import java.util.UUID;

public interface RegistrableProperty<O extends PropertiesOwner<O>, F extends PropertyFactory> extends Property<O> {

    default void onRegister(UUID uuid, F factory) {

    }

    default void onUnregister(UUID uuid) {

    }
}
