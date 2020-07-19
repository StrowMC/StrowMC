package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionPoints;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionPointsBean;
import fr.strow.persistence.dao.factions.FactionPointsDao;

import java.util.UUID;

public class FactionPointsProperty implements FactionPoints, ImplementationProperty<FactionPoints> {

    private final FactionPointsDao factionPointsDao;

    private UUID uuid;
    private int points;

    @Inject
    public FactionPointsProperty(FactionPointsDao factionPointsDao) {
        this.factionPointsDao = factionPointsDao;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

        FactionPointsBean bean = factionPointsDao.loadFactionPoints(uuid);
        points = bean.getPoints();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        FactionPointsBean bean = new FactionPointsBean(uuid, points);
        factionPointsDao.saveFactionPoints(bean);
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void addPoints(int points) {
        if (points > 0) {
            this.points += points;

            save(uuid);
        }
    }

    @Override
    public void removePoints(int points) {
        if (points > 0) {
            this.points -= points;

            save(uuid);
        }
    }
}
