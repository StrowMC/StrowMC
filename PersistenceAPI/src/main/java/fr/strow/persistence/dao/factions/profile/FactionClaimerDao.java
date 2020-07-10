/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:59
 */

package fr.strow.persistence.dao.factions.profile;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.beans.factions.profile.FactionClaimerBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionClaimerDao extends AbstractDao {

    @Inject
    public FactionClaimerDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public FactionClaimerBean loadClaimer(UUID uuid) {
        FactionClaimerBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionProfileBean profileBean = gson.fromJson(jedis.hget(Tables.FACTION_PROFILES, uuid.toString()), FactionProfileBean.class);

            boolean claimer = profileBean.isClaimer();
            bean = new FactionClaimerBean(uuid, claimer);
        }

        return bean;
    }

    public void saveClaimer(FactionClaimerBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID uuid = bean.getUuid();

            FactionProfileBean profileBean = gson.fromJson(jedis.hget(Tables.FACTION_PROFILES, uuid.toString()), FactionProfileBean.class);
            profileBean.setClaimer(bean.isClaimer());

            jedis.hset(Tables.FACTION_PROFILES, uuid.toString(), gson.toJson(profileBean));
        }
    }
}
