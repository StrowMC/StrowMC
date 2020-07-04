package fr.strow.core.module.mine.util;

import fr.strow.core.module.mine.configuration.MineConfiguration;
import fr.strow.core.utils.Cuboid;
import me.choukas.commands.utils.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class Mine {

    private static final Random random = new Random();

    private final Cuboid cuboid;

    private final String name;
    private int resetTime = 10;
    private final List<Tuple<Integer, Material>> mineConfiguration = new ArrayList<>();

    public Mine(Cuboid cuboid, String name){
        this.cuboid = cuboid;
        this.name = name;
    }

    public void reset(){
        Bukkit.broadcastMessage(String.format(MineConfiguration.resetMessage, this.name));
        cuboid.changeBlockType(this::getRandomBlock);
    }

    private Material getRandomBlock(){
        int rnd = random.nextInt(100) + 1;
        int total = 0;
        for (Tuple<Integer, Material> integerBlockTuple : mineConfiguration) {
            total += integerBlockTuple.getKey();
            if (rnd <= total) return integerBlockTuple.getValue();
        }
        throw new IllegalStateException("Error: Invalid block total rate for mine: " + name);
    }

    public int getResetTime(){
        return resetTime;
    }
    public String getName(){
        return name;
    }
    public void setResetTime(int resetTime){
        this.resetTime = resetTime;
    }
    public void addConfiguration(int rate, Material material){
        mineConfiguration.add(Tuple.of(rate, material));
    }
}
