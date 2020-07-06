package fr.strow.api.services;

import java.util.Collection;

public interface ServicesHandler {

    void registerService(Class<? extends Service> service);

    <T extends Service> T getService(Class<T> service);

    Collection<Service> getServices();
}
