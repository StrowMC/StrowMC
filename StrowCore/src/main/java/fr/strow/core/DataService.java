/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 19:49
 */

package fr.strow.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.persistence.bridges.PermissionsBridge;
import fr.strow.persistence.bridges.PlayersBridge;
import fr.strow.persistence.data.sql.SQLAccess;

public class DataService {

    private final Injector injector;

    @Inject
    public DataService(Injector injector) {
        this.injector = injector;
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
}
