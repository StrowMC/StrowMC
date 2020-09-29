package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionPrefix;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionPrefixBean;
import fr.strow.persistence.dao.factions.FactionPrefixDao;

import java.util.UUID;

public class FactionPrefixProperty implements FactionPrefix, ImplementationProperty<FactionPrefix> {

    private final FactionPrefixDao factionPrefixDao;

    private UUID uuid;
    private String prefix;

    @Inject
    public FactionPrefixProperty(FactionPrefixDao factionPrefixDao) {
        this.factionPrefixDao = factionPrefixDao;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

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
        if (!this.prefix.equals(prefix)) {
            this.prefix = prefix;

            save(uuid);
        }
    }
}
