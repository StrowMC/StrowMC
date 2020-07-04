package fr.strow.api.modules;

import com.google.inject.Injector;
import fr.strow.api.StrowPlugin;
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
public interface ModuleHandler {

    <T extends StrowModule> void enableModule(Class<T> clazz);

    void enableModules();

    <T extends StrowModule> void disableModule(Class<T> clazz);

    void disableModules();
}
