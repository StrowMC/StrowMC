/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 15:00
 */

package fr.strow.persistence.dao;

import com.google.gson.Gson;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.EconomyBean;
import fr.strow.persistence.beans.PlayerBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EconomyDao {

    private final RedisAccess redisAccess;
    private final Gson gson;

    public EconomyDao(RedisAccess redisAccess, Gson gson) {
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public EconomyBean loadEconomy(UUID uuid) {
        EconomyBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            PlayerBean playerBean = gson.fromJson(jedis.hget(Tables.PLAYERS, uuid.toString()), PlayerBean.class);

            int coins = playerBean.getCoins();
            bean = new EconomyBean(uuid, coins);
        }

        return bean;
    }

    public void saveEconomy(EconomyBean bean) {
        UUID uuid = bean.getUuid();

        try (Jedis jedis = redisAccess.getResource()) {
            PlayerBean playerBean = gson.fromJson(jedis.hget(Tables.PLAYERS, uuid.toString()), PlayerBean.class);
            playerBean.setCoins(bean.getCoins());

            jedis.hset(Tables.PLAYERS, uuid.toString(), gson.toJson(playerBean));
        }
    }

    public List<PlayerBean> getRichestPlayers(int n) {
        List<PlayerBean> beans;

        try (Jedis jedis = redisAccess.getResource()) {
            beans = jedis.hgetAll(Tables.PLAYERS).values()
                    .stream()
                    .map(s -> gson.fromJson(s, PlayerBean.class))
                    .collect(Collectors.toList());
        }

        return beans
                .stream()
                .sorted(Comparator.comparingInt(PlayerBean::getCoins))
                .limit(n)
                .collect(Collectors.toList());
    }
}
