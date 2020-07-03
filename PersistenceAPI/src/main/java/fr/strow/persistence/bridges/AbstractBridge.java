/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 23:44
 */

package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;

public abstract class AbstractBridge {

    protected final SQLAccess sqlAccess;
    protected final RedisAccess redisAccess;
    protected final Gson gson;

    @Inject
    public AbstractBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        this.sqlAccess = sqlAccess;
        this.redisAccess = redisAccess;
        this.gson = gson;
    }
}
