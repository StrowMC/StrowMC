/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 23:45
 */

package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.permissions.ProxyPermissionsBean;
import fr.strow.persistence.bridges.sql.permissions.ProxyPermissionsSQLDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class PermissionsBridge {

    private final ProxyPermissionsSQLDao proxyPermissionsSQLDao;

    private final RedisAccess redisAccess;
    private final Gson gson;

    @Inject
    public PermissionsBridge(RedisAccess redisAccess, Gson gson, ProxyPermissionsSQLDao proxyPermissionsSQLDao) {
        this.redisAccess = redisAccess;
        this.gson = gson;

        this.proxyPermissionsSQLDao = proxyPermissionsSQLDao;
    }

    public void loadPermissions() {
        Map<Integer, ProxyPermissionsBean> proxyPermissionsBeans = proxyPermissionsSQLDao.loadPermissions();

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<Integer, ProxyPermissionsBean> bean : proxyPermissionsBeans.entrySet()) {
                jedis.hset(Tables.PROXY_PERMISSIONS, bean.getKey().toString(), gson.toJson(bean.getValue()));
            }
        }
    }
}
