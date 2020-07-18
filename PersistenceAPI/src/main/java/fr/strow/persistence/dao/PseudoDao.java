package fr.strow.persistence.dao;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.PlayerBean;
import fr.strow.persistence.beans.PseudoBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class PseudoDao extends AbstractDao {

    @Inject
    public PseudoDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public PseudoBean loadPseudo(UUID uuid) {
        PseudoBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            PlayerBean playerBean = gson.fromJson(jedis.hget(Tables.PLAYERS, uuid.toString()), PlayerBean.class);

            String pseudo = playerBean.getPseudo();
            bean = new PseudoBean(uuid, pseudo);
        }

        return bean;
    }

    public void savePseudo(PseudoBean bean) {
        UUID uuid = bean.getUuid();

        try (Jedis jedis = redisAccess.getResource()) {
            PlayerBean playerBean = gson.fromJson(jedis.hget(Tables.PLAYERS, uuid.toString()), PlayerBean.class);
            playerBean.setPseudo(bean.getPseudo());

            jedis.hset(Tables.PLAYERS, uuid.toString(), gson.toJson(playerBean));
        }
    }
}
