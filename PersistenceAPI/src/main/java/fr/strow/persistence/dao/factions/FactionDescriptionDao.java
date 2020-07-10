package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionBean;
import fr.strow.persistence.beans.factions.FactionDescriptionBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionDescriptionDao extends AbstractDao {

    @Inject
    public FactionDescriptionDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public FactionDescriptionBean loadFactionDescription(UUID uuid) {
        FactionDescriptionBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, uuid.toString()), FactionBean.class);

            String description = factionBean.getName();
            bean = new FactionDescriptionBean(uuid, description);
        }

        return bean;
    }

    public void saveFactionDescription(FactionDescriptionBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID uuid = bean.getUuid();

            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, uuid.toString()), FactionBean.class);
            factionBean.setDescription(bean.getDescription());

            jedis.hset(Tables.FACTIONS, uuid.toString(), gson.toJson(factionBean));
        }
    }
}
