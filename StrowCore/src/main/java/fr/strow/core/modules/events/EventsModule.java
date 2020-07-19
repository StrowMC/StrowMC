package fr.strow.core.modules.events;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import fr.strow.api.module.StrowModule;
import fr.strow.api.property.PropertiesHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class EventsModule extends StrowModule {

    private final Injector injector;

    @Inject
    public EventsModule(Injector injector) {
        super(injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandService.class),
                injector.getInstance(PropertiesHandler.class));

        this.injector = injector;
    }
}
