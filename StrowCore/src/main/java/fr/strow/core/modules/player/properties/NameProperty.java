package fr.strow.core.modules.player.properties;

import com.google.inject.Inject;
import fr.strow.api.game.player.Name;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.NameBean;
import fr.strow.persistence.dao.NameDao;

import java.util.UUID;

public class NameProperty implements Name, ImplementationProperty<Name> {

    private final NameDao nameDao;

    private String name;

    @Inject
    public NameProperty(NameDao nameDao) {
        this.nameDao = nameDao;
    }

    @Override
    public boolean load(UUID uuid) {
        NameBean bean = nameDao.loadName(uuid);
        name = bean.getName();

        return true;
    }

    @Override
    public String getName() {
        return name;
    }
}
