package fr.strow.persistence.dao.sanctions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.sanctions.BanBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class BanDao extends AbstractDao {

    @Inject
    public BanDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public BanBean loadBan(UUID uuid) {
        BanBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            bean = gson.fromJson(jedis.hget(Tables.BANNED_PLAYERS, uuid.toString()), BanBean.class);
        }

        return bean;
    }

    public void saveBan(BanBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.BANNED_PLAYERS, bean.getUuid().toString(), gson.toJson(bean));
        }
    }
}
