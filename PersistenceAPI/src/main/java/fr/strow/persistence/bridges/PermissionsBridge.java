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
import fr.strow.persistence.beans.permissions.PermissionsBean;
import fr.strow.persistence.beans.permissions.ProxyPermissionsBean;
import fr.strow.persistence.dao.sql.permissions.ProxyPermissionsDao;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class PermissionsBridge extends AbstractBridge {

    private final ProxyPermissionsDao proxyPermissionsDao;

    @Inject
    public PermissionsBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson, ProxyPermissionsDao proxyPermissionsDao) {
        super(sqlAccess, redisAccess, gson);
        this.proxyPermissionsDao = proxyPermissionsDao;
    }

    public void loadPermissions() {
        Map<Integer, PermissionsBean> permissions = new HashMap<>();

        Map<Integer, ProxyPermissionsBean> proxyPermissionsBeans = proxyPermissionsDao.loadPermissions();

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<Integer, ProxyPermissionsBean> bean : proxyPermissionsBeans.entrySet()) {
                jedis.hset(Tables.PROXY_PERMISSIONS, bean.getKey().toString(), gson.toJson(bean.getValue()));
            }
        }
    }
}
