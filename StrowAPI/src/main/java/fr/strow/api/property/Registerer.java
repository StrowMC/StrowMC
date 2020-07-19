package fr.strow.api.property;

public interface Registerer<O extends PropertiesOwner<O>> {

    <P extends RegistrableProperty<O, EmptyPropertyFactory>> void registerProperty(Class<P> property);

    <F extends PropertyFactory, P extends RegistrableProperty<O, F>> void registerProperty(Class<P> property, F factory);

    <F extends PropertyFactory, P extends RegistrableProperty<O, F>>void unregisterProperty(Class<P> property);
}
