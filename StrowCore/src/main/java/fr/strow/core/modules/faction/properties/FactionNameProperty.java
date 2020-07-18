package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionName;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionNameBean;
import fr.strow.persistence.dao.factions.FactionNameDao;

import java.util.UUID;

public class FactionNameProperty extends ImplementationProperty implements FactionName {

    private final FactionNameDao factionNameDao;

    private String name;

    @Inject
    public FactionNameProperty(FactionNameDao factionNameDao) {
        this.factionNameDao = factionNameDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        FactionNameBean bean = factionNameDao.loadFactionName(factionUuid);
        name = bean.getName();

        return true;
    }

    @Override
    public void save(UUID factionUuid) {
        FactionNameBean bean = new FactionNameBean(factionUuid, name);
        factionNameDao.saveFactionName(bean);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
