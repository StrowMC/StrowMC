package fr.strow.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.properties.ImplementationProperty;

import java.util.ArrayList;
import java.util.List;

public class SamplePropertiesHandler implements PropertiesHandler {

    private static final List<Class<? extends ImplementationProperty>> definitions = new ArrayList<>();

    private final Injector injector;

    @Inject
    public SamplePropertiesHandler(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void registerProperty(Class<? extends ImplementationProperty> property) {
        definitions.add(property);
    }

    @Override
    public <T extends ImplementationProperty> T getProperty(Class<T> property) {
        return injector.getInstance(property);
    }

    @Override
    public List<ImplementationProperty> getProperties() {
        List<ImplementationProperty> properties = new ArrayList<>();

        for (Class<? extends ImplementationProperty> definition : definitions) {
            properties.add(getProperty(definition));
        }

        return properties;
    }
}
