package fr.strow.api.services;

import fr.strow.api.game.AbstractService;

public interface ServicesOwner {

    <T extends AbstractService> T getService(Class<T> service);
}
