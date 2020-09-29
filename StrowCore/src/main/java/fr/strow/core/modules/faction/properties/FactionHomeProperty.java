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

public class FactionHomeProperty implements FactionHome, ImplementationProperty<FactionHome> {

    private final FactionHomeDao factionHomeDao;
    private final LocationDao locationDao;

    private UUID factionUuid;
    private Location home;
    private int locationId;

    @Inject
    public FactionHomeProperty(FactionHomeDao factionHomeDao, LocationDao locationDao) {
        this.factionHomeDao = factionHomeDao;
        this.locationDao = locationDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        this.factionUuid = factionUuid;

        if (factionHomeDao.hasFactionHome(factionUuid)) {
            FactionHomeBean bean = factionHomeDao.loadFactionHome(factionUuid);

            int locationId = bean.getLocationId();
            LocationBean locationBean = locationDao.loadLocation(locationId);

            home = LocationUtils.getBukkitLocation(locationBean);

            this.locationId = locationId;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UUID factionUuid) {
        LocationBean locationBean = LocationUtils.getBeanLocation(locationId, home);
        locationDao.saveLocation(locationBean);
    }

    @Override
    public Location getHome() {
        return home;
    }

    @Override
    public void setHome(Location home) {
        if (this.home != home) {
            if (this.home == null) {
                // Insert home
                LocationBean locationBean = LocationUtils.getBeanLocation(home);
                locationId = locationDao.insertLocation(locationBean);

                FactionHomeBean bean = new FactionHomeBean(factionUuid, locationId);
                factionHomeDao.insertFactionHome(bean);

                this.home = home;
            } else {
                this.home = home;

                save(factionUuid);
            }
        }
    }

    @Override
    public void onUnregister(UUID uuid) {
        factionHomeDao.deleteFactionHome(uuid);
        locationDao.deleteLocation(locationId);
    }
}
