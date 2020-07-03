/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:26
 */

package fr.strow.core;
 
import com.google.inject.Injector;
import com.google.inject.Module;
import fr.strow.api.StrowAPI;
import fr.strow.api.StrowPlugin;
import fr.strow.api.modules.ModuleHandler;
import fr.strow.persistence.DataService;

import java.io.File;

public class StrowCore extends StrowPlugin implements StrowAPI {

    private DataService dataService;
    private Injector injector;
    private ModuleHandler moduleHandler;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        File dataFolder = getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        dataService = new DataService(dataFolder.toPath());
        dataService.start();

        injector = dataService.createChildInjector(new SampleBindModule(this));

        moduleHandler = new StrowModuleHandler(injector);
        moduleHandler.enableAllModules();
    }

    @Override
    public void onDisable() {
        dataService.shutdown();

        moduleHandler.disableAllModules();
    }

    @Override
    public Injector createInjector(Module... modules) {
        return injector.createChildInjector(modules);
    }
}
