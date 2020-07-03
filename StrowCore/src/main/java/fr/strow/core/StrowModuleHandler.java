package fr.strow.core;

import com.google.inject.Injector;
import fr.strow.api.modules.ModuleHandler;
import fr.strow.core.module.economy.EconomyModule;
import fr.strow.core.module.faction.FactionModule;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class StrowModuleHandler extends ModuleHandler {


    public StrowModuleHandler(Injector injector) {
        super(injector);
        registerModules();
    }

    @Override
    public void registerModules() {
        registerModule(FactionModule.class);
        registerModule(EconomyModule.class);
    }

    @Override
    public void enableAllModules() {
        modules.keySet().forEach(this::enableModule);
    }

    @Override
    public void disableAllModules() {
        modules.keySet().forEach(this::disableModule);
    }
}
