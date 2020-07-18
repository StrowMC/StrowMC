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

    public boolean hasDescription(UUID factionUuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            FactionDescriptionBean bean = loadFactionDescription(factionUuid);

            return bean.getDescription() != null;
        }
    }

    public FactionDescriptionBean loadFactionDescription(UUID factionUuid) {
        FactionDescriptionBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, factionUuid.toString()), FactionBean.class);

            String description = factionBean.getDescription();
            bean = new FactionDescriptionBean(factionUuid, description);
        }

        return bean;
    }

    public void saveFactionDescription(FactionDescriptionBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID factionUuid = bean.getUuid();

            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, factionUuid.toString()), FactionBean.class);
            factionBean.setDescription(bean.getDescription());

            jedis.hset(Tables.FACTIONS, factionUuid.toString(), gson.toJson(factionBean));
        }
    }
}
