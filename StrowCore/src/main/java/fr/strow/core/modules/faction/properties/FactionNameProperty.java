package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionName;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionNameBean;
import fr.strow.persistence.dao.factions.FactionNameDao;

import java.util.UUID;

public class FactionNameProperty implements FactionName, ImplementationProperty<FactionName> {

    private final FactionNameDao factionNameDao;

    private UUID uuid;
    private String name;

    @Inject
    public FactionNameProperty(FactionNameDao factionNameDao) {
        this.factionNameDao = factionNameDao;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

        FactionNameBean bean = factionNameDao.loadFactionName(uuid);
        name = bean.getName();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        FactionNameBean bean = new FactionNameBean(uuid, name);
        factionNameDao.saveFactionName(bean);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;

            save(uuid);
        }
    }
}
