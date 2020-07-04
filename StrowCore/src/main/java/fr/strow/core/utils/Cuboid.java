package fr.strow.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.function.Supplier;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class Cuboid {

    private Location location1;
    private Location location2;

    public Cuboid(Location location1, Location location2){
        this.location1 = location1;
        this.location2 = location2;
    }
    public Cuboid(World world, double x1, double y1, double z1, double x2, double y2, double z2){
        new Cuboid(new Location(world, x1, y1, z1), new Location(world, x2, y2, z2));
    }
    public Cuboid(String world, double x1, double y1, double z1, double x2, double y2, double z2){
        new Cuboid(new Location(Bukkit.getWorld(world), x1, y1, z1), new Location(Bukkit.getWorld(world), x2, y2, z2));
    }

    public boolean contains(Location location){
        return (location.getX() < location1.getX() && location.getX() > location2.getX()) || (location.getX() > location1.getX() && location.getX() < location2.getX())
                && (location.getY() < location1.getY() && location.getY() > location2.getY()) || (location.getY() > location1.getY() && location.getY() < location2.getY())
                && (location.getZ() < location1.getZ() && location.getZ() > location2.getZ()) || (location.getZ() > location1.getZ() && location.getZ() < location2.getZ());
    }

    public void changeBlockType(Supplier<Material> supplier){
        for(int x = (int) Math.min(location1.getX(), location2.getX()); x < Math.max(location1.getX(), location2.getX()); x++){
            for(int y = (int) Math.min(location1.getY(), location2.getY()); y < Math.max(location1.getY(), location2.getY()); y++){
                for(int z = (int) Math.min(location1.getZ(), location2.getZ()); z < Math.max(location1.getZ(), location2.getZ()); z++){
                    location1.getWorld().getBlockAt(x, y, z).setType(supplier.get());
                }
            }
        }
    }
    public void changeBlockType(Material material){
        changeBlockType(() -> material);
    }
}
