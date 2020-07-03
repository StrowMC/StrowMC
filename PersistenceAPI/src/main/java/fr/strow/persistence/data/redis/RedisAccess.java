/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:48
 */

package fr.strow.persistence.data.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisAccess {

    private final RedisCredentials credentials;
    private JedisPool pool;

    public RedisAccess(RedisCredentials credentials) {
        this.credentials = credentials;
    }

    public void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(16);

        pool = new JedisPool(
                config,
                credentials.getHost(),
                credentials.getPort(),
                credentials.getTimeout(),
                credentials.getPassword()
        );
    }

    public void closePool() {
        pool.close();
    }

    public Jedis getResource() {
        if (isClosed()) {
            initPool();
        }

        return pool.getResource();
    }

    private boolean isClosed() {
        return pool == null || pool.isClosed();
    }
}
