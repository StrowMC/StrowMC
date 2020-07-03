/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 15:00
 */

package fr.strow.persistence.dao.factions.players;

import com.google.gson.Gson;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.beans.factions.players.FactionUUIDBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionUUIDDao {

    private final RedisAccess redisAccess;
    private final Gson gson;

    public FactionUUIDDao(RedisAccess redisAccess, Gson gson) {
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public FactionUUIDBean loadUuid(UUID uuid) {
        FactionUUIDBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionProfileBean profileBean = gson.fromJson(jedis.hget(Tables.FACTION_PROFILES, uuid.toString()), FactionProfileBean.class);

            UUID factionUuid = profileBean.getFactionUuid();
            bean = new FactionUUIDBean(uuid, factionUuid);
        }

        return bean;
    }

    public void saveUuid(FactionUUIDBean bean) {
        UUID uuid = bean.getUuid();

        try (Jedis jedis = redisAccess.getResource()) {
            FactionProfileBean profileBean = gson.fromJson(jedis.hget(Tables.FACTION_PROFILES, uuid.toString()), FactionProfileBean.class);
            profileBean.setFactionUuid(bean.getFactionUuid());

            jedis.hset(Tables.PLAYERS, uuid.toString(), gson.toJson(profileBean));
        }
    }
}
