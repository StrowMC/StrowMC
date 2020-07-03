/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:47
 */

package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;

public class FactionProfilesBridge extends AbstractBridge {

    public FactionProfilesBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        super(sqlAccess, redisAccess, gson);
    }
}
