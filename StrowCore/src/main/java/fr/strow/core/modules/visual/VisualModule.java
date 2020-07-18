package fr.strow.core.modules.visual;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import fr.strow.api.module.StrowModule;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.visual.listeners.PlayerChatListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class VisualModule extends StrowModule {

    private final Injector injector;

    @Inject
    public VisualModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandService.class),
                injector.getInstance(PropertiesHandler.class));

        this.injector = injector;
    }

    @Override
    protected List<Listener> getListeners() {
        return Collections.singletonList(injector.getInstance(PlayerChatListener.class));
    }
}
