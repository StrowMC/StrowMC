package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionLeader;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionLeaderBean;
import fr.strow.persistence.dao.factions.FactionLeaderDao;

import java.util.UUID;

public class FactionLeaderProperty implements FactionLeader, ImplementationProperty<FactionLeader> {

    private final FactionLeaderDao factionLeaderDao;

    private UUID uuid;
    private UUID leader;

    @Inject
    public FactionLeaderProperty(FactionLeaderDao factionLeaderDao) {
        this.factionLeaderDao = factionLeaderDao;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

        FactionLeaderBean bean = factionLeaderDao.loadFactionLeader(uuid);
        leader = bean.getLeaderUuid();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        FactionLeaderBean bean = new FactionLeaderBean(uuid, leader);
        factionLeaderDao.saveFactionLeader(bean);
    }

    @Override
    public UUID getLeader() {
        return leader;
    }

    @Override
    public void setLeader(UUID leader) {
        if (this.leader != leader) {
            this.leader = leader;

            save(uuid);
        }
    }
}
