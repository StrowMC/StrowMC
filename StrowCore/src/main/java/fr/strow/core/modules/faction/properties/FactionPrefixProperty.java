package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionPrefix;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionPrefixBean;
import fr.strow.persistence.dao.factions.FactionPrefixDao;

import java.util.UUID;

public class FactionPrefixProperty extends ImplementationProperty implements FactionPrefix {

    private final FactionPrefixDao factionPrefixDao;

    private String prefix;

    @Inject
    public FactionPrefixProperty(FactionPrefixDao factionPrefixDao) {
        this.factionPrefixDao = factionPrefixDao;
    }

    @Override
    public boolean load(UUID uuid) {
        FactionPrefixBean bean = factionPrefixDao.loadFactionPrefix(uuid);

        prefix = bean.getPrefix();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        FactionPrefixBean bean = new FactionPrefixBean(uuid, prefix);

        factionPrefixDao.saveFactionPrefix(bean);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
