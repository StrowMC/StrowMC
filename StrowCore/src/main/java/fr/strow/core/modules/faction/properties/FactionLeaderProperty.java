package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionLeader;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionLeaderBean;
import fr.strow.persistence.dao.factions.FactionLeaderDao;

import java.util.UUID;

public class FactionLeaderProperty extends ImplementationProperty implements FactionLeader {

    private final FactionLeaderDao factionLeaderDao;

    private UUID leader;

    @Inject
    public FactionLeaderProperty(FactionLeaderDao factionLeaderDao) {
        this.factionLeaderDao = factionLeaderDao;
    }

    @Override
    public boolean load(UUID uuid) {
        FactionLeaderBean bean = factionLeaderDao.loadFactionLeaderUuid(uuid);

        leader = bean.getLeaderUuid();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        FactionLeaderBean bean = new FactionLeaderBean(uuid, leader);

        factionLeaderDao.saveFactionLeaderUuid(bean);
    }

    @Override
    public UUID getLeader() {
        return leader;
    }

    @Override
    public void setLeader(UUID leader) {
        this.leader = leader;
    }
}
