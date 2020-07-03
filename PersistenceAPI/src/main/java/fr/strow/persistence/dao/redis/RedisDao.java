/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 21:42
 */

package fr.strow.persistence.dao.redis;

import fr.strow.persistence.data.redis.RedisAccess;

public abstract class RedisDao {

    protected final RedisAccess access;

    public RedisDao(RedisAccess access) {
        this.access = access;
    }
}
