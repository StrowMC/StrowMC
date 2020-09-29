package fr.strow.core.modules.permissions;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.permissions.Group;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.module.StrowModule;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.permissions.properties.GroupProperty;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionsModule extends StrowModule {

    @Inject
    public PermissionsModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandService.class),
                injector.getInstance(PropertiesHandler.class));

        bindProperty(StrowPlayer.class, Group.class, GroupProperty.class);
    }
}
