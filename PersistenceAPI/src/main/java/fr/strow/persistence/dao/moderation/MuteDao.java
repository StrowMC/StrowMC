package fr.strow.persistence.dao.moderation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.moderation.MuteBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class MuteDao extends AbstractDao {

    @Inject
    public MuteDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public boolean hasMute(UUID uuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.MUTES, uuid.toString());
        }
    }

    public List<MuteBean> loadMutes(UUID uuid) {
        List<MuteBean> beans;
        final Type type = new TypeToken<List<MuteBean>>() {}.getType();

        try (Jedis jedis = redisAccess.getResource()) {
            beans = gson.fromJson(jedis.hget(Tables.MUTES, uuid.toString()), type);
        }

        return beans;
    }

    public void saveMutes(UUID uuid, List<MuteBean> beans) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.MUTES, uuid.toString(), gson.toJson(beans));
        }
    }
}
