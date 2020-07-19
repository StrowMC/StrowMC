package fr.strow.core.modules.events.koth;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import fr.strow.api.module.StrowModule;
import fr.strow.api.property.PropertiesHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class KOTHModule extends StrowModule {

    private final Injector injector;

    @Inject
    public KOTHModule(Injector injector) {
        super(injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandService.class),
                injector.getInstance(PropertiesHandler.class));

        this.injector = injector;
    }
}
