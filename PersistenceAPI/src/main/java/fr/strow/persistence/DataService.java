/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:48
 */

package fr.strow.persistence;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import fr.strow.persistence.bridges.PermissionsBridge;
import fr.strow.persistence.bridges.PlayersBridge;
import fr.strow.persistence.data.sql.SQLAccess;

import java.nio.file.Path;

public class DataService {

    private final Injector injector;

    public DataService(Path configPath) {
        injector = Guice.createInjector(new BindModule(configPath));
    }

    public void start() {
        injector.getInstance(SQLAccess.class).initPool();

        injector.getInstance(PermissionsBridge.class).loadPermissions();
        injector.getInstance(PlayersBridge.class).loadPlayers();
    }

    public void shutdown() {
        injector.getInstance(SQLAccess.class).closePool();

        injector.getInstance(PlayersBridge.class).unloadPlayers();
    }

    public Injector createChildInjector(Module... modules) {
        return injector.createChildInjector(modules);
    }
}
