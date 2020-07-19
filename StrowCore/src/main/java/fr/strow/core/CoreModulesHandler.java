package fr.strow.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.module.ModulesHandler;
import fr.strow.api.module.StrowModule;
import fr.strow.core.modules.shop.ShopModule;
import fr.strow.core.modules.visual.VisualModule;
import fr.strow.core.modules.economy.EconomyModule;
import fr.strow.core.modules.faction.FactionModule;
import fr.strow.core.modules.moderation.ModerationModule;
import fr.strow.core.modules.permissions.PermissionsModule;
import fr.strow.core.modules.player.PlayerModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class CoreModulesHandler implements ModulesHandler {

    private static final List<Class<? extends StrowModule>> modules = new ArrayList<>();

    static {
        modules.add(EconomyModule.class);
        modules.add(FactionModule.class);
        modules.add(ModerationModule.class);
        modules.add(PermissionsModule.class);
        modules.add(PlayerModule.class);
        modules.add(ShopModule.class);
        modules.add(VisualModule.class);

        //modules.add(PunishmentModule.class);
    }

    private final Injector injector;

    @Inject
    public CoreModulesHandler(Injector injector) {
        this.injector = injector;
    }

    @Override
    public <T extends StrowModule> void enableModule(Class<T> clazz) {
        T module = injector.getInstance(clazz);
        module.onEnable();
    }

    @Override
    public void enableModules() {
        for (Class<? extends StrowModule> module : modules) {
            enableModule(module);
        }
    }

    @Override
    public <T extends StrowModule> void disableModule(Class<T> clazz) {
        T module = injector.getInstance(clazz);
        module.onDisable();
    }

    @Override
    public void disableModules() {
        for (Class<? extends StrowModule> clazz : modules) {
            disableModule(clazz);
        }
    }
}