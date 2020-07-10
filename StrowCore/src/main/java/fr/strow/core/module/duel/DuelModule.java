package fr.strow.core.module.duel;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.core.module.duel.command.DuelCommand;
import fr.strow.core.module.duel.command.SetInventoryCommand;
import fr.strow.core.module.duel.listener.PlayerStateListener;
import fr.strow.core.module.duel.util.Duel;
import fr.strow.core.module.duel.util.DuelGroupManager;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelModule extends StrowModule {

    public static final Map<SetInventoryCommand.InventoryType, List<ItemStack[]>> inventories = new HashMap<>();
    private static final Map<Duel.Type, List<Tuple<Integer, Location>>> spawnLocations = new HashMap<>();
    private static Injector injector;

    public DuelModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesHandler.class)
        );
        DuelModule.injector = injector;
    }

    public static void setSpawnLocation(int index, Location location, Duel.Type type) {
        List<Tuple<Integer, Location>> list = spawnLocations.getOrDefault(type, new ArrayList<>());
        Tuple<Integer, Location> toRemove = null;
        for (Tuple<Integer, Location> tuple : list) {
            if (tuple.getKey() == index) {
                toRemove = tuple;
            }
        }
        if (toRemove != null) {
            list.remove(toRemove);
        }
        list.add(Tuple.of(index, location));
        spawnLocations.put(type, list);
    }

    @Override
    public void onEnable() {
        DuelGroupManager.injector = injector;
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Collections.singletonList(Tuple.of("duel", injector.getInstance(DuelCommand.class)));
    }

    @Override
    public List<Listener> getListeners() {
        return Collections.singletonList(injector.getInstance(PlayerStateListener.class));
    }
}
