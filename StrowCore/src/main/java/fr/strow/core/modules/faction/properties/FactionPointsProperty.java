package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionPoints;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionPointsBean;
import fr.strow.persistence.dao.factions.FactionPointsDao;

import java.util.UUID;

public class FactionPointsProperty extends ImplementationProperty implements FactionPoints {

    private final FactionPointsDao factionPointsDao;

    private int points;

    @Inject
    public FactionPointsProperty(FactionPointsDao factionPointsDao) {
        this.factionPointsDao = factionPointsDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        FactionPointsBean bean = factionPointsDao.loadFactionPoints(factionUuid);
        points = bean.getPoints();

        return true;
    }

    @Override
    public void save(UUID factionUuid) {
        FactionPointsBean bean = new FactionPointsBean(factionUuid, points);
        factionPointsDao.saveFactionPoints(bean);
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public void removePoints(int points) {
        this.points -= points;
    }
}
