/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 02/07/2020 21:12
 */

package fr.strow.persistence.dao.factions.players;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.beans.factions.players.FactionRoleBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionRoleDao {

    private final RedisAccess redisAccess;
    private final Gson gson;

    @Inject
    public FactionRoleDao(RedisAccess redisAccess, Gson gson) {
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public FactionRoleBean loadFactionRole(UUID uuid) {
        FactionRoleBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionProfileBean profileBean = gson.fromJson(jedis.hget(Tables.FACTION_PROFILES, uuid.toString()), FactionProfileBean.class);

            int id = profileBean.getRoleId();
            bean = new FactionRoleBean(uuid, id);
        }

        return bean;
    }

    public void saveFactionRole(FactionRoleBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID uuid = bean.getUuid();

            FactionProfileBean profileBean = gson.fromJson(jedis.hget(Tables.FACTION_PROFILES, uuid.toString()), FactionProfileBean.class);
            profileBean.setRoleId(bean.getRoleId());
        }
    }
}
