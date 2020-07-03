/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:59
 */

package fr.strow.persistence.dao.factions.players;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.players.FactionPermissionsBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

public class FactionPermissionsDao {

    private final RedisAccess redisAccess;
    private final Gson gson;

    @Inject
    public FactionPermissionsDao(RedisAccess redisAccess, Gson gson) {
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public FactionPermissionsBean loadFactionPermissions(int id) {
        FactionPermissionsBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            bean = gson.fromJson(jedis.hget(Tables.FACTION_PERMISSIONS, String.valueOf(id)), FactionPermissionsBean.class);
        }

        return bean;
    }
}
