package fr.strow.persistence.dao;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.LocationBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

public class LocationDao extends AbstractDao {

    @Inject
    public LocationDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public LocationBean loadLocation(int id) {
        LocationBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            bean = gson.fromJson(jedis.hget(Tables.LOCATIONS, String.valueOf(id)), LocationBean.class);
        }

        return bean;
    }

    public int saveLocation(LocationBean bean) {
        int id = bean.getId();

        try (Jedis jedis = redisAccess.getResource()) {
            if (id == 0) {
                id = jedis.hlen(Tables.LOCATIONS).intValue();
                bean.setId(id);
            }

            jedis.hset(Tables.LOCATIONS, String.valueOf(bean.getId()), gson.toJson(bean));
        }

        return id;
    }
}
