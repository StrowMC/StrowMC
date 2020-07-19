package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionWarps;
import fr.strow.api.property.EmptyPropertyFactory;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.core.utils.LocationUtils;
import fr.strow.persistence.beans.LocationBean;
import fr.strow.persistence.beans.factions.FactionWarpBean;
import fr.strow.persistence.dao.LocationDao;
import fr.strow.persistence.dao.factions.FactionWarpsDao;
import org.bukkit.Location;

import java.util.*;

public class FactionWarpsProperty implements FactionWarps, ImplementationProperty<FactionWarps> {

    private final FactionWarpsDao factionWarpsDao;
    private final LocationDao locationDao;

    private UUID factionUuid;
    private Map<String, Location> warps;

    @Inject
    public FactionWarpsProperty(FactionWarpsDao factionWarpsDao, LocationDao locationDao) {
        this.factionWarpsDao = factionWarpsDao;
        this.locationDao = locationDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        this.factionUuid = factionUuid;

        if (factionWarpsDao.hasFactionWarps(factionUuid)) {
            List<FactionWarpBean> beans = factionWarpsDao.loadFactionWarps(factionUuid);

            for (FactionWarpBean bean : beans) {
                int locationId = bean.getLocationId();
                LocationBean locationBean = locationDao.loadLocation(locationId);

                String name = bean.getName();
                Location location = LocationUtils.getBukkitLocation(locationBean);

                warps.put(name, location);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRegister(UUID factionUuid, EmptyPropertyFactory factory) {
        warps = new HashMap<>();
    }

    @Override
    public void onUnregister(UUID factionUuid) {
        factionWarpsDao.deleteFactionWarps(factionUuid);
    }

    @Override
    public void addWarp(String name, Location location) {
        if (!warps.containsKey(name)) {
            warps.put(name, location);

            LocationBean locationBean = LocationUtils.getBeanLocation(location);
            int locationId = locationDao.insertLocation(locationBean);

            FactionWarpBean bean = new FactionWarpBean(factionUuid, name, locationId);
            factionWarpsDao.insertFactionWarp(bean);
        }
    }

    @Override
    public void removeWarp(String name) {
        if (warps.containsKey(name)) {
            warps.remove(name);

            factionWarpsDao.deleteFactionWarp(factionUuid, name);
        }
    }

    @Override
    public boolean containsWarp(String name) {
        return warps.containsKey(name);
    }

    @Override
    public Map<String, Location> getWarps() {
        return warps;
    }
}
