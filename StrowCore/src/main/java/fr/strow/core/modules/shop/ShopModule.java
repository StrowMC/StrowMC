package fr.strow.core.modules.shop;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import fr.strow.api.module.StrowModule;
import fr.strow.api.property.PropertiesHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopModule extends StrowModule {

    private final Injector injector;

    @Inject
    public ShopModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandService.class),
                injector.getInstance(PropertiesHandler.class));

        this.injector = injector;
    }
}
