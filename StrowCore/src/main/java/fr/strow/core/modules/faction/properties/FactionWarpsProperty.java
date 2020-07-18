package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionWarps;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.core.utils.LocationUtils;
import fr.strow.persistence.beans.LocationBean;
import fr.strow.persistence.beans.factions.FactionWarpBean;
import fr.strow.persistence.dao.LocationDao;
import fr.strow.persistence.dao.factions.FactionWarpsDao;
import org.bukkit.Location;

import java.util.*;

public class FactionWarpsProperty extends ImplementationProperty implements FactionWarps {

    private final FactionWarpsDao factionWarpsDao;
    private final LocationDao locationDao;

    private Map<String, Location> warps;
    private Map<String, Integer> indexes;

    @Inject
    public FactionWarpsProperty(FactionWarpsDao factionWarpsDao, LocationDao locationDao) {
        this.factionWarpsDao = factionWarpsDao;
        this.locationDao = locationDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        if (factionWarpsDao.hasWarps(factionUuid)) {
            warps = new HashMap<>();
            indexes = new HashMap<>();

            List<FactionWarpBean> beans = factionWarpsDao.loadFactionWarps(factionUuid);

            for (FactionWarpBean bean : beans) {
                int locationId = bean.getLocationId();
                LocationBean locationBean = locationDao.loadLocation(locationId);

                String name = bean.getName();
                Location location = LocationUtils.getBukkitLocation(locationBean);

                warps.put(name, location);

                indexes.put(name, locationId);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UUID factionUuid) {
        List<FactionWarpBean> beans = new ArrayList<>();

        for (String name : warps.keySet()) {
            int index = indexes.get(name);
            Location location = warps.get(name);

            LocationBean locationBean = LocationUtils.getBeanLocation(index, location);
            int locationId = locationDao.saveLocation(locationBean);

            FactionWarpBean bean = new FactionWarpBean(factionUuid, name, locationId);
            beans.add(bean);
        }

        factionWarpsDao.saveFactionWarps(beans);
    }

    @Override
    public Map<String, Location> getWarps() {
        return warps;
    }

    @Override
    public void addWarp(String name, Location location) {
        warps.put(name, location);
        indexes.put(name, 0);
    }

    @Override
    public void removeWarp(String name) {
        warps.remove(name);
        indexes.remove(name);
    }
}
