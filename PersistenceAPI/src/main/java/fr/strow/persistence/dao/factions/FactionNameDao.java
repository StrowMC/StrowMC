package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.beans.factions.FactionBean;
import fr.strow.persistence.beans.factions.FactionNameBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionNameDao extends AbstractDao {

    @Inject
    public FactionNameDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public FactionNameBean loadFactionName(UUID uuid) {
        FactionNameBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, uuid.toString()), FactionBean.class);

            String name = factionBean.getName();
            bean = new FactionNameBean(uuid, name);
        }

        return bean;
    }

    public void saveFactionName(FactionNameBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID uuid = bean.getUuid();

            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, uuid.toString()), FactionBean.class);
            factionBean.setName(bean.getName());

            jedis.hset(Tables.FACTIONS, uuid.toString(), gson.toJson(factionBean));
        }
    }
}
