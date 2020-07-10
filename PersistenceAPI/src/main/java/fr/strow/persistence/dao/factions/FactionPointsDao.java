package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionBean;
import fr.strow.persistence.beans.factions.FactionPointsBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionPointsDao extends AbstractDao {

    @Inject
    public FactionPointsDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public FactionPointsBean loadFactionPoints(UUID uuid) {
        FactionPointsBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, uuid.toString()), FactionBean.class);

            int points = factionBean.getPoints();
            bean = new FactionPointsBean(uuid, points);
        }

        return bean;
    }

    public void saveFactionPoints(FactionPointsBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID uuid = bean.getUuid();

            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, uuid.toString()), FactionBean.class);
            factionBean.setPoints(bean.getPoints());

            jedis.hset(Tables.FACTIONS, uuid.toString(), gson.toJson(factionBean));
        }
    }
}
