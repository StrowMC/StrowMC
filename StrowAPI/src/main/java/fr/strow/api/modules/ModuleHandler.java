package fr.strow.api.modules;

import com.google.inject.Injector;
import fr.strow.api.StrowPlugin;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.properties.PropertiesCollection;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hokkaydo on 24-06-2020.
 */
public abstract class ModuleHandler {

    protected final Map<Class<? extends StrowModule>, StrowModule> modules = new HashMap<>();

    private static final JavaPlugin plugin = JavaPlugin.getPlugin(StrowPlugin.class);

    private final Injector injector;

    public ModuleHandler(Injector injector){
        this.injector = injector;
    }

    public abstract void registerModules();

    public abstract void enableAllModules();

    public abstract void disableAllModules();

    public <T extends StrowModule> void enableModule(Class<T> classModule){
        if(modules.containsKey(classModule)) {
            StrowModule module = modules.get(classModule);
            module.onEnable();
            if(module.getListeners() != null) module.getListeners().forEach(l -> plugin.getServer().getPluginManager().registerEvents(l, plugin));
            if(module.getCommands() != null) module.getCommands().forEach(t -> injector.getInstance(CommandsManager.class).registerCommand(t));
            if(module.getConfigurations() != null) module.getConfigurations().forEach(AbstractConfiguration::loadConfig);
            if(module.getProperties() != null) module.getProperties().forEach(p -> injector.getInstance(PropertiesCollection.class).registerProperty(p));
            plugin.getLogger().info(module.getClass() + " enabled");
        }
    }

    public <T extends StrowModule> void disableModule(Class<T> classModule){
        if(modules.containsKey(classModule)) {
            StrowModule module = modules.get(classModule);
            module.getListeners().forEach(HandlerList::unregisterAll);
            module.getCommands().forEach(t -> injector.getInstance(CommandsManager.class).unregisterCommand(t.getKey()));
            module.getConfigurations().forEach(AbstractConfiguration::saveConfig);
            module.onDisable();
            plugin.getLogger().info(module.getClass() + " disabled");
        }
    }

    protected  <T extends StrowModule> void registerModule(Class<T> moduleClass){
        T module = null;
        try {
            module = moduleClass.getConstructor(Injector.class).newInstance(injector);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        modules.put(moduleClass, module);
    }
}
