/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:59
 */

package fr.strow.persistence.dao.factions.players;

import com.google.gson.Gson;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.beans.factions.players.FactionPowerBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionPowerDao {

    private final RedisAccess redisAccess;
    private final Gson gson;

    public FactionPowerDao(RedisAccess redisAccess, Gson gson) {
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public FactionPowerBean loadPower(UUID uuid) {
        FactionPowerBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionProfileBean profileBean = gson.fromJson(jedis.hget(Tables.FACTION_PROFILES, uuid.toString()), FactionProfileBean.class);

            int power = profileBean.getPower();
            bean = new FactionPowerBean(uuid, power);
        }

        return bean;
    }

    public void savePower(FactionPowerBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID uuid = bean.getUuid();

            FactionProfileBean profileBean = gson.fromJson(jedis.hget(Tables.FACTION_PROFILES, uuid.toString()), FactionProfileBean.class);
            profileBean.setPower(bean.getPower());

            jedis.hset(Tables.PLAYERS, uuid.toString(), gson.toJson(profileBean));
        }
    }
}
