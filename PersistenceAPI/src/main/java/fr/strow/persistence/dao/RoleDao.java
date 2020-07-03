/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 23:11
 */

package fr.strow.persistence.dao;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.beans.PlayerBean;
import fr.strow.persistence.beans.RoleBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class RoleDao {

    private static final String REDIS_KEY = "players";

    private final RedisAccess redisAccess;
    private final Gson gson;

    @Inject
    public RoleDao(RedisAccess redisAccess, Gson gson) {
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public RoleBean loadRole(UUID uuid) {
        RoleBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            PlayerBean playerBean = gson.fromJson(jedis.hget(REDIS_KEY, uuid.toString()), PlayerBean.class);

            int roleId = playerBean.getRoleId();
            bean = new RoleBean(uuid, roleId);
        }

        return bean;
    }

    public void saveRole(RoleBean bean) {
        UUID uuid = bean.getUuid();

        try (Jedis jedis = redisAccess.getResource()) {
            PlayerBean playerBean = gson.fromJson(jedis.hget(REDIS_KEY, uuid.toString()), PlayerBean.class);
            playerBean.setRoleId(bean.getRoleId());

            jedis.hset(REDIS_KEY, uuid.toString(), gson.toJson(playerBean));
        }
    }
}
