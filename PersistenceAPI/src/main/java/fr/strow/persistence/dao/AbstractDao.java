package fr.strow.persistence.dao;

import com.google.gson.Gson;
import fr.strow.persistence.data.redis.RedisAccess;

public abstract class AbstractDao {

    protected final RedisAccess redisAccess;
    protected final Gson gson;

    public AbstractDao(RedisAccess redisAccess, Gson gson) {
        this.redisAccess = redisAccess;
        this.gson = gson;
    }
}
