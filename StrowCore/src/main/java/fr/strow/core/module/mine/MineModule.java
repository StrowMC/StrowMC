package fr.strow.core.module.mine;

import com.google.inject.Injector;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.core.StrowCore;
import fr.strow.core.module.mine.command.MineCommand;
import fr.strow.core.module.mine.util.Mine;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineModule extends StrowModule {

    private static final List<Mine> mines = new ArrayList<>();

    public MineModule(Injector injector) {
        super(injector);
    }

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                for (Mine mine : mines) {
                    if(i % mine.getResetTime() == 0){
                        mine.reset();
                        Bukkit.broadcastMessage("§aLa mine " + mine.getName() + " a été réinitialisée");
                    }
                }
                i++;
            }
        }.runTaskTimer(JavaPlugin.getPlugin(StrowCore.class), 0L, 60*20L);
    }

    @Override
    public void onDisable() {
        //TODO: Save mines
    }

    @Override
    public List<Listener> getListeners() {
        return null;
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Collections.singletonList(Tuple.of("mine", new MineCommand()));
    }

    @Override
    public List<AbstractConfiguration> getConfigurations() {
        return null;
    }

    @Override
    public List<AbstractProperty> getProperties() {
        return null;
    }

    public static void registerMine(Mine mine){
        Mine tempMine = null;
        for (Mine mine1 : mines) {
            if(mine1.getName().equalsIgnoreCase(mine.getName())) {
                tempMine = mine1;
                break;
            }
        }
        if(tempMine != null) mines.remove(tempMine);
        mines.add(mine);
    }
    public static void unregisterMine(Mine mine){
        mines.remove(mine);
    }
    public static Optional<Mine> getByName(String name){
        for (Mine mine : mines) {
            if(mine.getName().equalsIgnoreCase(name)) return Optional.of(mine);
        }
        return Optional.empty();
    }
}
