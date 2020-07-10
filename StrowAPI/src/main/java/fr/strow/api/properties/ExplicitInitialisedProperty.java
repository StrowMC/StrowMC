package fr.strow.api.properties;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;

public interface ExplicitInitialisedProperty<T extends PropertyFactory> extends Property {

    @SuppressWarnings("unchecked")
    default T factory() {
        T factory = null;
        TypeToken<T> type = new TypeToken<T>() {
        };

        try {
            factory = (T) type.getType().getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return factory;
    }
}
