/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 23:25
 */

package fr.strow.persistence.dao;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.permissions.PermissionsBean;
import fr.strow.persistence.beans.permissions.ProxyPermissionsBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

public class PermissionsDao {

    private final RedisAccess redisAccess;
    private final Gson gson;

    @Inject
    public PermissionsDao(RedisAccess redisAccess, Gson gson) {
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public PermissionsBean loadPermissions(int id) {
        PermissionsBean bean;

        try (Jedis jedis = redisAccess.getResource()){
            ProxyPermissionsBean proxyPermissionsBean = gson.fromJson(jedis.hget(Tables.PROXY_PERMISSIONS, String.valueOf(id)), ProxyPermissionsBean.class);

            bean = new PermissionsBean(proxyPermissionsBean);
        }

        return bean;
    }
}
