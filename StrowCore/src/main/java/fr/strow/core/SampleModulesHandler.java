package fr.strow.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.modules.ModulesHandler;
import fr.strow.api.modules.StrowModule;
import fr.strow.core.module.economy.EconomyModule;
import fr.strow.core.module.faction.FactionModule;
import fr.strow.core.module.miscelaneous.MiscelaneousModule;
import fr.strow.core.module.player.PlayerModule;
import fr.strow.core.utils.module.UtilModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class SampleModulesHandler implements ModulesHandler {

    private static final List<Class<? extends StrowModule>> definitions = new ArrayList<>();

    static {
        definitions.add(PlayerModule.class);
        definitions.add(EconomyModule.class);
        definitions.add(FactionModule.class);
        definitions.add(UtilModule.class);
        definitions.add(MiscelaneousModule.class);
    }

    private final Injector injector;

    @Inject
    public SampleModulesHandler(Injector injector) {
        this.injector = injector;
    }

    @Override
    public <T extends StrowModule> void enableModule(Class<T> clazz) {
        T module = injector.getInstance(clazz);
        module.onEnable();
    }

    @Override
    public void enableModules() {
        for (Class<? extends StrowModule> clazz : definitions) {
            enableModule(clazz);
        }
    }

    @Override
    public <T extends StrowModule> void disableModule(Class<T> clazz) {
        T module = injector.getInstance(clazz);
        module.onDisable();
    }

    @Override
    public void disableModules() {
        for (Class<? extends StrowModule> clazz : definitions) {
            disableModule(clazz);
        }
    }
}