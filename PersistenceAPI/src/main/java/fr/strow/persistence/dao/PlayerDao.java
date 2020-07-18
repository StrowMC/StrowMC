package fr.strow.persistence.dao;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.PlayerBean;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerDao extends AbstractDao {

    @Inject
    public PlayerDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public boolean playerExists(UUID uuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.PLAYERS, uuid.toString());
        }
    }

    public UUID getUUIDFromPseudo(String pseudo) {
        try (Jedis jedis = redisAccess.getResource()) {
            Optional<PlayerBean> optionalPlayer = jedis.hgetAll(Tables.PLAYERS)
                    .values()
                    .stream()
                    .map(player ->
                            gson.fromJson(player, PlayerBean.class))
                    .filter(player ->
                            player.getPseudo().equals(pseudo))
                    .findAny();

            if (optionalPlayer.isPresent()) {
                return optionalPlayer.get().getUuid();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public void createPlayer(PlayerBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.PLAYERS, bean.getUuid().toString(), gson.toJson(bean));
        }
    }

    public List<UUID> getPlayers() {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hgetAll(Tables.PLAYERS)
                    .keySet()
                    .stream()
                    .map(UUID::fromString)
                    .collect(Collectors.toList());
        }
    }
}
