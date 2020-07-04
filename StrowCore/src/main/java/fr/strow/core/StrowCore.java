/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:26
 */

package fr.strow.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.strow.api.StrowPlugin;
import fr.strow.api.modules.ModuleHandler;

import java.io.File;

public class StrowCore extends StrowPlugin {

    private DataService dataService;
    private ModuleHandler moduleHandler;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        File dataFolder = getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        Injector injector = Guice.createInjector(new SampleBindModule(this));

        dataService = injector.getInstance(DataService.class);
        dataService.start();

        moduleHandler = new SampleModuleHandler(injector);
        moduleHandler.enableModules();
    }

    @Override
    public void onDisable() {
        dataService.shutdown();

        moduleHandler.disableModules();
    }
}
