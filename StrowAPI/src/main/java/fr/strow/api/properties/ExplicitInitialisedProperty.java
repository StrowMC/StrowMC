package fr.strow.api.properties;

public interface ExplicitInitialisedProperty<T extends PropertyFactory> {

    void init(T factory);
}
