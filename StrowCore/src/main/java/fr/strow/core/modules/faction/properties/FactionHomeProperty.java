package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionHome;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.core.utils.LocationUtils;
import fr.strow.persistence.beans.LocationBean;
import fr.strow.persistence.beans.factions.FactionHomeBean;
import fr.strow.persistence.dao.LocationDao;
import fr.strow.persistence.dao.factions.FactionHomeDao;
import org.bukkit.Location;

import java.util.UUID;

public class FactionHomeProperty extends ImplementationProperty implements FactionHome {

    private final FactionHomeDao factionHomeDao;
    private final LocationDao locationDao;

    private Location home;
    private int index;

    @Inject
    public FactionHomeProperty(FactionHomeDao factionHomeDao, LocationDao locationDao) {
        this.factionHomeDao = factionHomeDao;
        this.locationDao = locationDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        if (factionHomeDao.hasHome(factionUuid)) {
            FactionHomeBean bean = factionHomeDao.loadFactionHome(factionUuid);

            int locationId = bean.getLocationId();
            LocationBean locationBean = locationDao.loadLocation(locationId);

            home = LocationUtils.getBukkitLocation(locationBean);

            index = locationId;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UUID factionUuid) {
        LocationBean locationBean = LocationUtils.getBeanLocation(index, home);
        int locationId = locationDao.saveLocation(locationBean);

        FactionHomeBean bean = new FactionHomeBean(factionUuid, locationId);
        factionHomeDao.saveFactionHome(bean);
    }

    @Override
    public Location getHome() {
        return home;
    }

    @Override
    public void setHome(Location home) {
        this.home = home;
    }
}
