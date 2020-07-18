package fr.strow.core.utils;

import fr.strow.persistence.beans.LocationBean;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static Location getBukkitLocation(LocationBean bean) {
        return new Location(
                Bukkit.getWorld(bean.getWorld()),
                bean.getX(),
                bean.getY(),
                bean.getZ(),
                bean.getYaw(),
                bean.getPitch());
    }

    public static LocationBean getBeanLocation(int id, Location location) {
        return new LocationBean(
                id,
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    public static LocationBean getBeanLocation(Location location) {
        return getBeanLocation(-1, location);
    }
}
