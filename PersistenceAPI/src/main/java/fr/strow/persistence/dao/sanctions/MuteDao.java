package fr.strow.persistence.dao.sanctions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.sanctions.MuteBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class MuteDao extends AbstractDao {

    @Inject
    public MuteDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public MuteBean loadMute(UUID uuid) {
        MuteBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            bean = gson.fromJson(jedis.hget(Tables.MUTED_PLAYERS, uuid.toString()), MuteBean.class);
        }

        return bean;
    }

    public void saveMute(MuteBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.MUTED_PLAYERS, bean.getUuid().toString(), gson.toJson(bean));
        }
    }
}
